package com.lionheartpacific.practices.repository

import org.junit.jupiter.api.BeforeEach
import javax.sql.DataSource
import kotlin.time.Clock
import kotlin.time.Instant

abstract class AbstractPetRepositoryTest<TRepository : Any> : AbstractRepositoryTest() {
    protected val testClock = object : TestClock {
        private var now: Instant = Instant.parse("2020-01-01T00:00:00Z")
        override fun now(): Instant = now

        override fun setNow(value: Instant) {
            now = value
        }
    }

    protected lateinit var repository: TRepository

    protected abstract fun createRepository(dataSource: DataSource, clock: Clock): TRepository

    @BeforeEach
    fun initializeRepository() {
        repository = createRepository(dataSource, testClock)
    }
}
