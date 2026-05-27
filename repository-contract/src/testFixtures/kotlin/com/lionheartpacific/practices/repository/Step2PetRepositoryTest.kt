package com.lionheartpacific.practices.repository

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull

abstract class Step2PetRepositoryTest<TRepository : Step2PetRepository> : Step1PetRepositoryTest<TRepository>() {
    @Test
    fun `a pet's weight is recorded`() {
        val userOneId = 10L
        val id = repository.create(PetRequest(name = "Fluffy"), userOneId)

        repository.updateWeight(id, 12.5, userOneId)

        expectThat(repository.findById(id)).isNotNull().get { weight }.isEqualTo(12.5)
    }

    @Test
    fun `a pet's weight can be updated`() {
        val userOneId = 10L
        val id = repository.create(PetRequest(name = "Fluffy"), userOneId)
        repository.updateWeight(id, 12.5, userOneId)

        repository.updateWeight(id, 15.0, userOneId)

        expectThat(repository.findById(id)).isNotNull().get { weight }.isEqualTo(15.0)
    }
}
