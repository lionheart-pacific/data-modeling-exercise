package com.lionheartpacific.practices.repository

import org.junit.jupiter.api.BeforeEach
import javax.sql.DataSource

abstract class AbstractRepositoryTest {
    companion object {
        private val testDatabase = ThreadLocal.withInitial { TestDatabase() }
    }

    protected val dataSource: DataSource get() = testDatabase.get().dataSource

    @BeforeEach
    fun resetDatabase() {
        testDatabase.get().resetData()
    }
}
