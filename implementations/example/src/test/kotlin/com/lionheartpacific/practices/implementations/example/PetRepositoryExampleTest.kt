package com.lionheartpacific.practices.implementations.example

import com.lionheartpacific.practices.repository.Step1PetRepository
import com.lionheartpacific.practices.repository.Step1PetRepositoryTest
import org.springframework.jdbc.core.simple.JdbcClient
import javax.sql.DataSource
import kotlin.time.Clock

class PetRepositoryExampleTest : Step1PetRepositoryTest() {
    override fun createRepository(dataSource: DataSource, clock: Clock): Step1PetRepository =
        PetRepositoryExample(JdbcClient.create(dataSource), clock)
}
