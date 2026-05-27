package com.lionheartpacific.practices.implementations.versionedobjects

import com.lionheartpacific.practices.repository.Step1PetRepositoryTest
import org.springframework.jdbc.core.simple.JdbcClient
import javax.sql.DataSource
import kotlin.time.Clock

class PetRepositoryVersionedObjectsTest : Step1PetRepositoryTest<PetRepositoryVersionedObjects>() {
    override fun createRepository(dataSource: DataSource, clock: Clock) =
        PetRepositoryVersionedObjects(JdbcClient.create(dataSource), clock)
}
