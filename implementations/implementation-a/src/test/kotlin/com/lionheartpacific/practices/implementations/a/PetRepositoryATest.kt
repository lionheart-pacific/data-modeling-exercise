package com.lionheartpacific.practices.implementations.a

import com.lionheartpacific.practices.repository.Step1PetRepositoryTest
import org.springframework.jdbc.core.simple.JdbcClient
import javax.sql.DataSource
import kotlin.time.Clock

class PetRepositoryATest : Step1PetRepositoryTest<PetRepositoryA>() {
    override fun createRepository(dataSource: DataSource, clock: Clock) =
        PetRepositoryA(JdbcClient.create(dataSource), clock)
}
