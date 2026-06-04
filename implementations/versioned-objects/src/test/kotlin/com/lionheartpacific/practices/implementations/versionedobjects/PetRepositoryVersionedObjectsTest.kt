package com.lionheartpacific.practices.implementations.versionedobjects

import com.lionheartpacific.practices.repository.Step4PetRepositoryTest
import org.springframework.jdbc.core.simple.JdbcClient
import javax.sql.DataSource
import kotlin.time.Clock

class PetRepositoryVersionedObjectsTest : Step4PetRepositoryTest<PetRepositoryVersionedObjects>() {
    override fun createRepository(dataSource: DataSource, clock: Clock) =
        PetRepositoryVersionedObjects(JdbcClient.create(dataSource), clock)
}
