package com.lionheartpacific.practices.implementations.a

import com.lionheartpacific.practices.repository.Pet
import com.lionheartpacific.practices.repository.PetRepository
import java.util.concurrent.ConcurrentHashMap

class PetRepositoryA : PetRepository {
    override fun save(pet: Pet): Pet {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Pet? {
        TODO("Not yet implemented")
    }
}
