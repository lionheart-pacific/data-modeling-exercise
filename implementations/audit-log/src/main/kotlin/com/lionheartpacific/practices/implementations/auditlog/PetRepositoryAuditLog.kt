package com.lionheartpacific.practices.implementations.auditlog

import com.lionheartpacific.practices.repository.Pet
import com.lionheartpacific.practices.repository.PetRequest
import com.lionheartpacific.practices.repository.PetStatus
import com.lionheartpacific.practices.repository.Step1PetRepository
import com.lionheartpacific.practices.repository.Step2PetRepository
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.jdbc.support.GeneratedKeyHolder
import kotlin.time.Clock

class PetRepositoryAuditLog(
    private val jdbcClient: JdbcClient,
    private val clock: Clock,
) : Step2PetRepository {
    override fun create(request: PetRequest, actorId: Long): Long {
        val keyHolder = GeneratedKeyHolder()
        jdbcClient.sql("INSERT INTO pets (name, status) VALUES (:name, :status)")
            .param("name", request.name)
            .param("status", PetStatus.AVAILABLE.name)
            .update(keyHolder, "id")
        return keyHolder.key?.toLong() ?: error("INSERT did not return a generated key")
    }

    override fun updateStatus(id: Long, status: PetStatus, actorId: Long) {
        jdbcClient.sql("UPDATE pets SET status = :status WHERE id = :id")
            .param("id", id)
            .param("status", status.name)
            .update()
    }

    override fun findById(id: Long): Pet? {
        return jdbcClient.sql("SELECT id, name, status, weight FROM pets WHERE id = :id")
            .param("id", id)
            .query { resultSet, _ ->
                Pet(
                    id = resultSet.getLong("id"),
                    name = resultSet.getString("name"),
                    status = PetStatus.valueOf(resultSet.getString("status")),
                    weight = resultSet.getDouble("weight")
                )

            }
            .optional()
            .orElse(null)
    }

    override fun updateWeight(id: Long, weight: Double, actorId: Long) {
        jdbcClient.sql("UPDATE pets SET weight = :weight WHERE id = :id")
            .param("id", id)
            .param("weight", weight)
            .update()
    }
}
