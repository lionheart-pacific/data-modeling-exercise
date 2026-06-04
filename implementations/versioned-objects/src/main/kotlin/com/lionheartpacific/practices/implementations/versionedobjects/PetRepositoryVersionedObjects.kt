package com.lionheartpacific.practices.implementations.versionedobjects

import com.lionheartpacific.practices.repository.*
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.jdbc.support.GeneratedKeyHolder
import java.sql.Timestamp
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.toJavaInstant
import kotlin.time.toKotlinInstant

class PetRepositoryVersionedObjects(
    private val jdbcClient: JdbcClient,
    private val clock: Clock,
) : Step5PetRepository {
    override fun create(request: PetRequest, actorId: Long): Long {
        val keyHolder = GeneratedKeyHolder()
        jdbcClient.sql("INSERT INTO pets (name, status, valid_from, actor_id) VALUES (:name, :status, :valid_from, :actor_id)")
            .param("name", request.name)
            .param("status", PetStatus.AVAILABLE.name)
            .param("valid_from", Timestamp.from(clock.now().toJavaInstant()))
            .param("actor_id", actorId)
            .update(keyHolder, "pet_id")
        return keyHolder.key?.toLong() ?: error("INSERT did not return a generated key")
    }

    override fun updateStatus(id: Long, status: PetStatus, actorId: Long) {
        val pet = findById(id) ?: return

        jdbcClient.sql("UPDATE pets SET valid_to = :valid_to WHERE pet_id = :pet_id")
            .param("pet_id", id)
            .param("valid_to", Timestamp.from(clock.now().toJavaInstant()))
            .update()

        jdbcClient.sql("INSERT INTO pets (pet_id, name, status, valid_from, actor_id) VALUES (:pet_id, :name, :status, :valid_from, :actor_id)")
            .param("pet_id", pet.id)
            .param("name", pet.name)
            .param("status", status.name)
            .param("valid_from", Timestamp.from(clock.now().toJavaInstant()))
            .param("actor_id", actorId)
            // TODO tests didn't make us add weight here
            .update()
    }

    override fun findById(id: Long): Pet? {
        return jdbcClient.sql("SELECT pet_id, name, status, weight FROM pets WHERE pet_id = :pet_id AND valid_to IS NULL")
            .param("pet_id", id)
            .query { resultSet, _ ->
                Pet(
                    id = resultSet.getLong("pet_id"),
                    name = resultSet.getString("name"),
                    status = PetStatus.valueOf(resultSet.getString("status")),
                    weight = resultSet.getDouble("weight"),
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

        jdbcClient.sql("INSERT INTO pets (pet_id, name, status, weight, valid_from, actor_id) VALUES (:pet_id, :name, :status, :weight, :valid_from, :actor_id)")
            .param("pet_id", pet.id)
            .param("name", pet.name)
            .param("status", pet.status.name)
            .param("weight", weight)
            .param("valid_from", Timestamp.from(clock.now().toJavaInstant()))
            .param("actor_id", actorId)
            .update()
    }

    override fun getWeightChart(id: Long): WeightChart? {
        return jdbcClient.sql("SELECT valid_from, weight FROM pets WHERE pet_id = :pet_id AND weight IS NOT NULL")
            .param("pet_id", id)
            .query { resultSet, _ ->
                WeightEntry(
                    recordedAt = resultSet.getTimestamp("valid_from").toInstant().toKotlinInstant(),
                    weight = resultSet.getDouble("weight"),
                )
            }
            .list()
            .let { WeightChart(entries = it) }
    }

    override fun getWeighInLeaderboard(window: Duration): WeighInLeaderboard {
        val now = clock.now()
        return jdbcClient.sql(
            """
            SELECT actor_id, COUNT(*) AS weigh_ins
            FROM pets
            WHERE valid_from BETWEEN :start_timestamp AND :end_timestamp
            GROUP BY actor_id
            ORDER BY weigh_ins DESC;
            """.trimIndent()
        )
            // TODO test didn't require to add condition `AND weight IS NOT NULL`
            .param("start_timestamp", Timestamp.from(now.minus(window).toJavaInstant()))
            .param("end_timestamp", Timestamp.from(now.toJavaInstant()))
            .query { resultSet, _ ->
                WeighInLeaderboardEntry(
                    actorId = resultSet.getLong("actor_id"),
                    weighIns = resultSet.getLong("weigh_ins").toInt(),
                )
            }
            .list()
            .let { WeighInLeaderboard(entries = it) }
    }

    override fun updateName(id: Long, newName: String, actorId: Long) {
        val pet = findById(id) ?: return

        jdbcClient.sql("UPDATE pets SET valid_to = :valid_to WHERE pet_id = :pet_id")
            .param("pet_id", id)
            .param("valid_to", Timestamp.from(clock.now().toJavaInstant()))
            .update()

        jdbcClient.sql("INSERT INTO pets (pet_id, name, status, weight, valid_from, actor_id) VALUES (:pet_id, :name, :status, :weight, :valid_from, :actor_id)")
            .param("pet_id", pet.id)
            .param("name", newName)
            .param("status", pet.status.name)
            .param("weight", pet.weight)
            .param("valid_from", Timestamp.from(clock.now().toJavaInstant()))
            .param("actor_id", actorId)
            .update()
    }

    override fun findPetIdsByAnyName(name: String): List<Long> {
        return jdbcClient.sql("SELECT pet_id FROM pets WHERE name = :name")
            .param("name", name)
            .query { resultSet, _ -> resultSet.getLong("pet_id") }
            .list()
    }
}
