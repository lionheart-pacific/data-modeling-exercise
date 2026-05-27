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
        jdbcClient.sql("INSERT INTO pets (name, weight, status) VALUES (:name, :weight, :status)")
            .param("name", request.name)
            .param("weight", request.weight)
            .param("status", PetStatus.AVAILABLE.name)
            .update(keyHolder, "id")
        return keyHolder.key?.toLong()
            ?: error("INSERT did not return a generated key")
    }

    override fun updateWeight(id: Long, weight: Double, actorId: Long) {
        jdbcClient.sql("UPDATE pets SET weight = :weight WHERE id = :id")
            .param("weight", weight)
            .param("id", id)
            .update()
    }

    override fun updateStatus(id: Long, status: PetStatus, actorId: Long) {
        jdbcClient.sql("UPDATE pets SET status = :status WHERE id = :id")
            .param("status", status.name)
            .param("id", id)
            .update()
    }

    override fun findById(id: Long): Pet? =
        jdbcClient.sql("SELECT id, name, weight, status FROM pets WHERE id = :id")
            .param("id", id)
            .query { resultSet, _ ->
                Pet(
                    id = resultSet.getLong("id"),
                    name = resultSet.getString("name"),
                    weight = resultSet.getDouble("weight"),
                    status = PetStatus.valueOf(resultSet.getString("status")),
                )
            }
            .optional()
            .orElse(null)
}
