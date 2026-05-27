package com.lionheartpacific.practices.implementations.b

import com.lionheartpacific.practices.repository.AbstractPetRepositoryTest
import com.lionheartpacific.practices.repository.PetRepository

class PetRepositoryBTest : AbstractPetRepositoryTest() {
    override fun newRepository(): PetRepository = PetRepositoryB()
}
