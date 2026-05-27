package com.lionheartpacific.practices.repository

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

abstract class AbstractPetRepositoryTest {
    protected lateinit var repository: PetRepository

    protected abstract fun newRepository(): PetRepository

    @BeforeEach
    fun setUp() {
        repository = newRepository()
    }

    @Test
    fun `findById returns null when no pet has been saved`() {
        expectThat(repository.findById(1L)).isNull()
    }

    @Test
    fun `save then findById returns the saved pet`() {
        val pet = Pet(id = 1L, name = "Fido", weight = 12.5)

        repository.save(pet)

        expectThat(repository.findById(1L)).isEqualTo(pet)
    }

    @Test
    fun `save overwrites an existing pet with the same id`() {
        val original = Pet(id = 1L, name = "Fido", weight = 12.5)
        val updated = original.copy(name = "Rex", weight = 15.0)

        repository.save(original)
        repository.save(updated)

        expectThat(repository.findById(1L)).isEqualTo(updated)
    }
}
