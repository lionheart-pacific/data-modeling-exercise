package com.lionheartpacific.practices.implementations.example

import com.lionheartpacific.practices.repository.AbstractPetRepositoryTest
import com.lionheartpacific.practices.repository.PetRepository
import org.springframework.jdbc.core.simple.JdbcClient

class PetRepositoryExampleTest : AbstractPetRepositoryTest() {
    override fun newRepository(): PetRepository =
        PetRepositoryExample(JdbcClient.create(dataSource))
}
