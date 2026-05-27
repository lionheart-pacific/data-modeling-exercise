package com.lionheartpacific.practices.implementations.b

import com.lionheartpacific.practices.repository.Pet
import com.lionheartpacific.practices.repository.PetRepository
import java.util.concurrent.ConcurrentHashMap

class PetRepositoryB : PetRepository {
    override fun save(pet: Pet): Pet {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Pet? {
        TODO("Not yet implemented")
    }
}
