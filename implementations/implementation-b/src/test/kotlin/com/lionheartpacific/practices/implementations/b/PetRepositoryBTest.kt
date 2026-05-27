package com.lionheartpacific.practices.implementations.b

import com.lionheartpacific.practices.repository.Step1PetRepository
import com.lionheartpacific.practices.repository.Step1PetRepositoryTest
import org.springframework.jdbc.core.simple.JdbcClient
import javax.sql.DataSource
import kotlin.time.Clock

class PetRepositoryBTest : Step1PetRepositoryTest() {
    override fun createRepository(dataSource: DataSource, clock: Clock): Step1PetRepository =
        PetRepositoryB(JdbcClient.create(dataSource), testClock)
}
