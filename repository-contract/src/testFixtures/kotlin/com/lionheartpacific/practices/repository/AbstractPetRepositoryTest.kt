package com.lionheartpacific.practices.repository

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull

abstract class AbstractPetRepositoryTest {
    protected lateinit var repository: PetRepository

    protected abstract fun newRepository(): PetRepository

    @BeforeEach
    fun setUp() {
        repository = newRepository()
    }

    @Test
    fun `getting a pet that doesn't exist returns null`() {
        expectThat(repository.findById(1L)).isNull()
    }

    @Test
    fun `a created pet can be retrieved`() {
        val request = PetRequest(name = "Fido", weight = 12.5)

        val id = repository.create(request)

        expectThat(repository.findById(id)).isNotNull().and {
            get { this.id }.isEqualTo(id)
            get { name }.isEqualTo("Fido")
            get { weight }.isEqualTo(12.5)
        }
    }

    @Test
    fun `a pet's weight can be updated`() {
        val id = repository.create(PetRequest(name = "Fido", weight = 12.5))

        repository.updateWeight(id, 15.0)

        expectThat(repository.findById(id))
            .isNotNull()
            .get { weight }.isEqualTo(15.0)
    }
}
