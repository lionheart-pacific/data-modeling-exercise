package com.lionheartpacific.practices.implementations.a

import com.lionheartpacific.practices.repository.AbstractPetRepositoryTest
import com.lionheartpacific.practices.repository.PetRepository
import org.springframework.jdbc.core.simple.JdbcClient

class PetRepositoryATest : AbstractPetRepositoryTest() {
    override fun newRepository(): PetRepository =
        PetRepositoryA(JdbcClient.create(dataSource))
}
