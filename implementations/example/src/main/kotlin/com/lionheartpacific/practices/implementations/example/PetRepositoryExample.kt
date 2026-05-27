package com.lionheartpacific.practices.implementations.example

import com.lionheartpacific.practices.repository.Pet
import com.lionheartpacific.practices.repository.PetRepository
import com.lionheartpacific.practices.repository.PetRequest
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.jdbc.support.GeneratedKeyHolder

class PetRepositoryExample(private val jdbcClient: JdbcClient) : PetRepository {
    override fun create(request: PetRequest): Long {
        val keyHolder = GeneratedKeyHolder()
        jdbcClient.sql("INSERT INTO pets (name, weight) VALUES (:name, :weight)")
            .param("name", request.name)
            .param("weight", request.weight)
            .update(keyHolder, "id")
        return keyHolder.key?.toLong()
            ?: error("INSERT did not return a generated key")
    }

    override fun updateWeight(id: Long, weight: Double) {
        jdbcClient.sql("UPDATE pets SET weight = :weight WHERE id = :id")
            .param("weight", weight)
            .param("id", id)
            .update()
    }

    override fun findById(id: Long): Pet? =
        jdbcClient.sql("SELECT id, name, weight FROM pets WHERE id = :id")
            .param("id", id)
            .query { resultSet, _ ->
                Pet(
                    id = resultSet.getLong("id"),
                    name = resultSet.getString("name"),
                    weight = resultSet.getDouble("weight"),
                )
            }
            .optional()
            .orElse(null)
}
