package com.lionheartpacific.practices.implementations.example

import com.lionheartpacific.practices.repository.Step1PetRepositoryTest
import org.springframework.jdbc.core.simple.JdbcClient
import javax.sql.DataSource
import kotlin.time.Clock

class PetRepositoryExampleTest : Step1PetRepositoryTest<PetRepositoryExample>() {
    override fun createRepository(dataSource: DataSource, clock: Clock) =
        PetRepositoryExample(JdbcClient.create(dataSource), clock)
}
