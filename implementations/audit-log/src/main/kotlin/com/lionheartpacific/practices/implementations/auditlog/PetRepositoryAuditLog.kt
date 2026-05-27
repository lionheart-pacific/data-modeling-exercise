package com.lionheartpacific.practices.implementations.auditlog

import com.lionheartpacific.practices.repository.Pet
import com.lionheartpacific.practices.repository.PetRequest
import com.lionheartpacific.practices.repository.PetStatus
import com.lionheartpacific.practices.repository.Step1PetRepository
import org.springframework.jdbc.core.simple.JdbcClient
import kotlin.time.Clock

class PetRepositoryAuditLog(
    private val jdbcClient: JdbcClient,
    private val clock: Clock,
) : Step1PetRepository {
    override fun create(request: PetRequest, actorId: Long): Long {
        TODO("Not yet implemented")
    }

    override fun updateStatus(id: Long, status: PetStatus, actorId: Long) {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Pet? {
        TODO("Not yet implemented")
    }
}
