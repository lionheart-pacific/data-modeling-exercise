package com.lionheartpacific.practices.repository

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isNotNull
import kotlin.time.Instant

abstract class Step2PetRepositoryTest : AbstractPetRepositoryTest<Step2PetRepository>() {
    @Test
    fun `creating a pet records the initial weight in the chart`() {
        val actor = Actor(10L, "George")
        val createdAt = Instant.parse("2026-01-01T00:00:00Z")
        testClock.setNow(createdAt)

        val id = repository.create(PetRequest(name = "Fluffy", weight = 10.0), actor)

        expectThat(repository.getWeightChart(id)).isNotNull().get { entries }.containsExactly(
            WeightEntry(recordedAt = createdAt, weight = 10.0),
        )
    }

    @Test
    fun `updating weight appends to the chart in order`() {
        val actor = Actor(10L, "George")
        testClock.setNow(Instant.parse("2026-01-01T00:00:00Z"))
        val id = repository.create(PetRequest(name = "Fluffy", weight = 10.0), actor)
        testClock.setNow(Instant.parse("2026-02-01T00:00:00Z"))
        repository.updateWeight(id, 12.5, actor)
        testClock.setNow(Instant.parse("2026-03-01T00:00:00Z"))
        repository.updateWeight(id, 15.0, actor)

        expectThat(repository.getWeightChart(id)).isNotNull().get { entries }.containsExactly(
            WeightEntry(recordedAt = Instant.parse("2026-01-01T00:00:00Z"), weight = 10.0),
            WeightEntry(recordedAt = Instant.parse("2026-02-01T00:00:00Z"), weight = 12.5),
            WeightEntry(recordedAt = Instant.parse("2026-03-01T00:00:00Z"), weight = 15.0),
        )
    }
}
