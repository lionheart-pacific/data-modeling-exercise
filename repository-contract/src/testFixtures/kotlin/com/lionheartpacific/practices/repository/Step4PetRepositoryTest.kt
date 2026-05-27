package com.lionheartpacific.practices.repository

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.containsExactlyInAnyOrder
import strikt.assertions.doesNotContain
import strikt.assertions.isEmpty
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant

abstract class Step4PetRepositoryTest<TRepository : Step4PetRepository> : Step3PetRepositoryTest<TRepository>() {
    @Test
    fun `the leaderboard is empty when no pets have been weighed`() {
        expectThat(repository.getWeighInLeaderboard(30.days).entries).isEmpty()
    }

    @Test
    fun `the window controls which weigh-ins are counted`() {
        val userOneId = 10L
        val pet = repository.create(PetRequest("Fluffy"), userOneId)

        testClock.setNow(Instant.parse("2026-01-01T00:00:00Z"))
        repository.updateWeight(pet, 10.0, userOneId)

        testClock.setNow(Instant.parse("2026-01-20T00:00:00Z"))
        repository.updateWeight(pet, 11.0, userOneId)

        testClock.setNow(Instant.parse("2026-02-01T00:00:00Z"))

        expectThat(repository.getWeighInLeaderboard(60.days).entries)
            .containsExactly(WeighInLeaderboardEntry(actorId = userOneId, weighIns = 2))

        expectThat(repository.getWeighInLeaderboard(15.days).entries)
            .containsExactly(WeighInLeaderboardEntry(actorId = userOneId, weighIns = 1))
    }

    @Test
    fun `the leaderboard counts each actor's weigh-ins`() {
        val userOneId = 1L
        val userTwoId = 2L
        val userThreeId = 3L

        val fluffy = repository.create(PetRequest("Fluffy"), userOneId)
        val sprout = repository.create(PetRequest("Sprout"), userTwoId)

        testClock.setNow(Instant.parse("2026-01-15T00:00:00Z"))
        repository.updateWeight(fluffy, 10.0, userOneId)
        repository.updateWeight(fluffy, 11.0, userOneId)
        repository.updateWeight(sprout, 5.0, userTwoId)
        repository.updateWeight(fluffy, 12.0, userThreeId)
        repository.updateWeight(sprout, 6.0, userThreeId)
        repository.updateWeight(sprout, 7.0, userThreeId)

        testClock.setNow(Instant.parse("2026-01-16T00:00:00Z"))

        expectThat(repository.getWeighInLeaderboard(30.days).entries)
            .containsExactlyInAnyOrder(
                WeighInLeaderboardEntry(actorId = userOneId, weighIns = 2),
                WeighInLeaderboardEntry(actorId = userTwoId, weighIns = 1),
                WeighInLeaderboardEntry(actorId = userThreeId, weighIns = 3),
            )
    }

    @Test
    fun `the leaderboard orders actors by weigh-in count descending`() {
        val userOneId = 1L
        val userTwoId = 2L
        val userThreeId = 3L

        val pet = repository.create(PetRequest("Fluffy"), userTwoId)

        testClock.setNow(Instant.parse("2026-01-15T00:00:00Z"))
        repository.updateWeight(pet, 10.0, userTwoId)
        repository.updateWeight(pet, 11.0, userThreeId)
        repository.updateWeight(pet, 12.0, userThreeId)
        repository.updateWeight(pet, 13.0, userThreeId)
        repository.updateWeight(pet, 14.0, userOneId)
        repository.updateWeight(pet, 15.0, userOneId)

        testClock.setNow(Instant.parse("2026-01-16T00:00:00Z"))

        expectThat(repository.getWeighInLeaderboard(30.days).entries.map { it.actorId })
            .containsExactly(userThreeId, userOneId, userTwoId)
    }

    @Test
    fun `actors with no in-window weigh-ins are excluded`() {
        val userOneId = 1L
        val userTwoId = 2L
        val pet = repository.create(PetRequest("Fluffy"), userTwoId)

        testClock.setNow(Instant.parse("2026-01-01T00:00:00Z"))
        repository.updateWeight(pet, 10.0, userTwoId)

        testClock.setNow(Instant.parse("2026-02-15T00:00:00Z"))
        repository.updateWeight(pet, 11.0, userOneId)

        testClock.setNow(Instant.parse("2026-03-01T00:00:00Z"))

        expectThat(repository.getWeighInLeaderboard(30.days).entries.map { it.actorId })
            .doesNotContain(userTwoId)
    }
}
