package com.lionheartpacific.practices.implementations.a

import com.lionheartpacific.practices.repository.AbstractPetRepositoryTest
import com.lionheartpacific.practices.repository.PetRepository

class PetRepositoryATest : AbstractPetRepositoryTest() {
    override fun newRepository(): PetRepository = PetRepositoryA()
}
