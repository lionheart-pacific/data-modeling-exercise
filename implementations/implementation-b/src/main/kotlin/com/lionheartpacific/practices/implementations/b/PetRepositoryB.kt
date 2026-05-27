package com.lionheartpacific.practices.implementations.b

import com.lionheartpacific.practices.repository.Pet
import com.lionheartpacific.practices.repository.PetRepository
import java.util.concurrent.ConcurrentHashMap

class PetRepositoryB : PetRepository {
    private val store = ConcurrentHashMap<Long, Pet>()

    override fun save(pet: Pet): Pet {
        store[pet.id] = pet
        return pet
    }

    override fun findById(id: Long): Pet? = store[id]
}
