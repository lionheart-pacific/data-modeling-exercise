package com.lionheartpacific.practices.repository

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull

abstract class Step1PetRepositoryTest<TRepository : Step1PetRepository> : AbstractPetRepositoryTest<TRepository>() {
    @Test
    fun `getting a pet that doesn't exist returns null`() {
        expectThat(repository.findById(1L)).isNull()
    }

    @Test
    fun `a created pet can be retrieved`() {
        val userOneId = 10L
        val request = PetRequest(name = "Fluffy", weight = 12.5)

        val id = repository.create(request, userOneId)

        expectThat(repository.findById(id)).isNotNull().and {
            get { this.id }.isEqualTo(id)
            get { name }.isEqualTo("Fluffy")
            get { weight }.isEqualTo(12.5)
            get { status }.isEqualTo(PetStatus.AVAILABLE)
        }
    }

    @Test
    fun `a pet's weight can be updated`() {
        val userOneId = 10L
        val id = repository.create(PetRequest(name = "Fluffy", weight = 12.5), userOneId)

        repository.updateWeight(id, 15.0, userOneId)

        expectThat(repository.findById(id))
            .isNotNull()
            .get { weight }.isEqualTo(15.0)
    }

    @Test
    fun `a pet's status can be toggled between available and adopted`() {
        val userOneId = 10L
        val id = repository.create(PetRequest(name = "Fluffy", weight = 12.5), userOneId)

        repository.updateStatus(id, PetStatus.ADOPTED, userOneId)
        expectThat(repository.findById(id)).isNotNull().get { status }.isEqualTo(PetStatus.ADOPTED)

        repository.updateStatus(id, PetStatus.AVAILABLE, userOneId)
        expectThat(repository.findById(id)).isNotNull().get { status }.isEqualTo(PetStatus.AVAILABLE)
    }
}
