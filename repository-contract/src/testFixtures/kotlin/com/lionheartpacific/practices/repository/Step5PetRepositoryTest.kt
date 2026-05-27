package com.lionheartpacific.practices.repository

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.containsExactlyInAnyOrder
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull

abstract class Step5PetRepositoryTest<TRepository : Step5PetRepository> : Step4PetRepositoryTest<TRepository>() {
    @Test
    fun `updating a name changes what findById returns`() {
        val userOneId = 10L
        val id = repository.create(PetRequest("Biscuit"), userOneId)

        repository.updateName(id, "Tabitha", userOneId)

        expectThat(repository.findById(id)).isNotNull().get { name }.isEqualTo("Tabitha")
    }

    @Test
    fun `a pet's current name can be found`() {
        val userOneId = 10L
        val id = repository.create(PetRequest("Biscuit"), userOneId)

        expectThat(repository.findPetIdsByAnyName("Biscuit")).containsExactlyInAnyOrder(id)
    }

    @Test
    fun `a pet's former name can be found`() {
        val userOneId = 10L
        val id = repository.create(PetRequest("Biscuit"), userOneId)
        repository.updateName(id, "Tabitha", userOneId)

        expectThat(repository.findPetIdsByAnyName("Biscuit")).containsExactlyInAnyOrder(id)
    }

    @Test
    fun `a name shared in history across pets surfaces all of them`() {
        val userOneId = 10L
        val firstId = repository.create(PetRequest("Biscuit"), userOneId)
        repository.updateName(firstId, "Tabitha", userOneId)
        val secondId = repository.create(PetRequest("Biscuit"), userOneId)

        expectThat(repository.findPetIdsByAnyName("Biscuit"))
            .containsExactlyInAnyOrder(firstId, secondId)
    }

    @Test
    fun `a name no pet has ever had returns empty`() {
        val userOneId = 10L
        repository.create(PetRequest("Biscuit"), userOneId)

        expectThat(repository.findPetIdsByAnyName("Snickerdoodle")).isEmpty()
    }
}
