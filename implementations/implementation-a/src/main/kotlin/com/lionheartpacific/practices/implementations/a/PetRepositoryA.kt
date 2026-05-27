package com.lionheartpacific.practices.implementations.a

import com.lionheartpacific.practices.repository.Pet
import com.lionheartpacific.practices.repository.PetRepository
import com.lionheartpacific.practices.repository.PetRequest

class PetRepositoryA : PetRepository {
    override fun create(request: PetRequest): Long {
        TODO("Not yet implemented")
    }

    override fun updateWeight(id: Long, weight: Double) {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Pet? {
        TODO("Not yet implemented")
    }
}
