package com.lionheartpacific.practices.implementations.example

import com.lionheartpacific.practices.repository.Pet
import com.lionheartpacific.practices.repository.PetRequest
import com.lionheartpacific.practices.repository.PetStatus
import com.lionheartpacific.practices.repository.Step1PetRepository
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.jdbc.support.GeneratedKeyHolder
import kotlin.time.Clock

class PetRepositoryExample(
    private val jdbcClient: JdbcClient,
    private val clock: Clock,
) : Step1PetRepository {
    override fun create(request: PetRequest, actorId: Long): Long {
        val keyHolder = GeneratedKeyHolder()
        jdbcClient.sql("INSERT INTO pets (name, status) VALUES (:name, :status)")
            .param("name", request.name)
            .param("status", PetStatus.AVAILABLE.name)
            .update(keyHolder, "id")
        return keyHolder.key?.toLong()
            ?: error("INSERT did not return a generated key")
    }

    override fun updateStatus(id: Long, status: PetStatus, actorId: Long) {
        jdbcClient.sql("UPDATE pets SET status = :status WHERE id = :id")
            .param("status", status.name)
            .param("id", id)
            .update()
    }

    override fun findById(id: Long): Pet? =
        jdbcClient.sql("SELECT id, name, status FROM pets WHERE id = :id")
            .param("id", id)
            .query { resultSet, _ ->
                Pet(
                    id = resultSet.getLong("id"),
                    name = resultSet.getString("name"),
                    status = PetStatus.valueOf(resultSet.getString("status")),
                    weight = null,
                )
            }
            .optional()
            .orElse(null)
}
