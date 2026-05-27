package com.lionheartpacific.practices.implementations.auditlog

import com.lionheartpacific.practices.repository.Step1PetRepositoryTest
import org.springframework.jdbc.core.simple.JdbcClient
import javax.sql.DataSource
import kotlin.time.Clock

class PetRepositoryAuditLogTest : Step1PetRepositoryTest<PetRepositoryAuditLog>() {
    override fun createRepository(dataSource: DataSource, clock: Clock) =
        PetRepositoryAuditLog(JdbcClient.create(dataSource), clock)
}
