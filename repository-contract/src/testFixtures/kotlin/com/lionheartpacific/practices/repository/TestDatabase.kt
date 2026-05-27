package com.lionheartpacific.practices.repository

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.testcontainers.postgresql.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import javax.sql.DataSource

class TestDatabase {
    private val container: PostgreSQLContainer =
        PostgreSQLContainer(DockerImageName.parse("postgres:17"))
            .apply { start() }

    private val hikariDataSource: HikariDataSource = HikariDataSource(HikariConfig().apply {
        jdbcUrl = container.jdbcUrl
        username = container.username
        password = container.password
        maximumPoolSize = 3
    }).also { dataSource ->
        Flyway.configure()
            .dataSource(dataSource)
            .load()
            .migrate()
    }

    val dataSource: DataSource get() = hikariDataSource

    private val reset = DatabaseReset(dataSource)

    fun resetData() = reset.reset()

    init {
        Runtime.getRuntime().addShutdownHook(Thread { close() })
    }

    private fun close() {
        hikariDataSource.close()
        container.stop()
    }
}
