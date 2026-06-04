package com.lionheartpacific.practices.implementations.auditlog

import com.lionheartpacific.practices.repository.*
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.jdbc.support.GeneratedKeyHolder
import java.sql.Timestamp
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.toJavaInstant
import kotlin.time.toKotlinInstant

class PetRepositoryAuditLog(
    private val jdbcClient: JdbcClient,
    private val clock: Clock,
) : Step4PetRepository {
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

        jdbcClient.sql("INSERT INTO weight_history (pet_id, weight, recorded_at, actor_id) VALUES (:pet_id, :weight, :recorded_at, :actor_id)")
            .param("pet_id", id)
            .param("weight", weight)
            .param("recorded_at", Timestamp.from(clock.now().toJavaInstant()))
            .param("actor_id", actorId)
            .update()
    }

    override fun getWeightChart(id: Long): WeightChart? {
        return jdbcClient.sql("SELECT weight, recorded_at FROM weight_history WHERE pet_id = :pet_id")
            .param("pet_id", id)
            .query { resultSet, _ ->
                WeightEntry(
                    recordedAt = resultSet.getTimestamp("recorded_at").toInstant().toKotlinInstant(),
                    weight = resultSet.getDouble("weight")
                )
            }
            .list()
            .let { WeightChart(entries = it) }
    }

    override fun getWeighInLeaderboard(window: Duration): WeighInLeaderboard {
        val startTime = clock.now().minus(window)
        return jdbcClient.sql(
            """
            SELECT 
                actor_id, 
                COUNT(*) AS weigh_in_count
            FROM weight_history
            WHERE recorded_at >= :start_time
            GROUP BY actor_id
            ORDER BY weigh_in_count DESC;
            """.trimIndent()
        )
            .param("start_time", Timestamp.from(startTime.toJavaInstant()))
            .query { resultSet, _ ->
                WeighInLeaderboardEntry(
                    actorId = resultSet.getLong("actor_id"),
                    weighIns = resultSet.getInt("weigh_in_count"),
                )
            }
            .list()
            .let { WeighInLeaderboard(entries = it) }
    }
}
