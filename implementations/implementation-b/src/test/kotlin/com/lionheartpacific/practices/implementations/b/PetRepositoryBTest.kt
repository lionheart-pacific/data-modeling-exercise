package com.lionheartpacific.practices.implementations.b

import com.lionheartpacific.practices.repository.Step1PetRepositoryTest
import org.springframework.jdbc.core.simple.JdbcClient
import javax.sql.DataSource
import kotlin.time.Clock

class PetRepositoryBTest : Step1PetRepositoryTest<PetRepositoryB>() {
    override fun createRepository(dataSource: DataSource, clock: Clock) =
        PetRepositoryB(JdbcClient.create(dataSource), clock)
}
