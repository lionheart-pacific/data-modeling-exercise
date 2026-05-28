package com.lionheartpacific.practices.implementations.versionedobjects

import com.lionheartpacific.practices.repository.Pet
import com.lionheartpacific.practices.repository.PetRequest
import com.lionheartpacific.practices.repository.PetStatus
import com.lionheartpacific.practices.repository.Step2PetRepository
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.jdbc.support.GeneratedKeyHolder
import java.sql.Timestamp
import kotlin.time.Clock
import kotlin.time.toJavaInstant

class PetRepositoryVersionedObjects(
    private val jdbcClient: JdbcClient,
    private val clock: Clock,
) : Step2PetRepository {
    override fun create(request: PetRequest, actorId: Long): Long {
        val keyHolder = GeneratedKeyHolder()
        jdbcClient.sql("INSERT INTO pets (name, status, valid_from) VALUES (:name, :status, :valid_from)")
            .param("name", request.name)
            .param("status", PetStatus.AVAILABLE.name)
            .param("valid_from", Timestamp.from(clock.now().toJavaInstant()))
            .update(keyHolder, "pet_id")
        return keyHolder.key?.toLong() ?: error("INSERT did not return a generated key")
    }

    override fun updateStatus(id: Long, status: PetStatus, actorId: Long) {
        val pet = findById(id) ?: return

        jdbcClient.sql("UPDATE pets SET valid_to = :valid_to WHERE pet_id = :pet_id")
            .param("pet_id", id)
            .param("valid_to", Timestamp.from(clock.now().toJavaInstant()))
            .update()

        jdbcClient.sql("INSERT INTO pets (pet_id, name, status, valid_from) VALUES (:pet_id, :name, :status, :valid_from)")
            .param("pet_id", pet.id)
            .param("name", pet.name)
            .param("status", status.name)
            .param("valid_from", Timestamp.from(clock.now().toJavaInstant()))
            // TODO tests didn't make us add weight here
            .update()
    }

    override fun findById(id: Long): Pet? {
        return jdbcClient.sql("SELECT pet_id, name, status, weight FROM pets WHERE pet_id = :pet_id AND valid_to IS NULL")
            .param("pet_id", id)
            .query { result, _ ->
                Pet(
                    id = result.getLong("pet_id"),
                    name = result.getString("name"),
                    status = PetStatus.valueOf(result.getString("status")),
                    weight = result.getDouble("weight"),
                )

            }
            .optional()
            .orElse(null)
    }

    override fun updateWeight(id: Long, weight: Double, actorId: Long) {
        val pet = findById(id) ?: return

        jdbcClient.sql("UPDATE pets SET valid_to = :valid_to WHERE pet_id = :pet_id")
            .param("pet_id", id)
            .param("valid_to", Timestamp.from(clock.now().toJavaInstant()))
            .update()

        jdbcClient.sql("INSERT INTO pets (pet_id, name, status, weight, valid_from) VALUES (:pet_id, :name, :status, :weight, :valid_from)")
            .param("pet_id", pet.id)
            .param("name", pet.name)
            .param("status", pet.status.name)
            .param("weight", weight)
            .param("valid_from", Timestamp.from(clock.now().toJavaInstant()))
            .update()
    }
}
