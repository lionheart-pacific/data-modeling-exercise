package com.lionheartpacific.practices.repository

import javax.sql.DataSource

class DatabaseReset(private val dataSource: DataSource) {
    @Suppress("SqlSourceToSinkFlow")
    private val truncateSql: String = run {
        val excludedSchemas = listOf("pg_catalog", "information_schema")
        val excludedTables = listOf("flyway_schema_history")
        val schemasInClause = excludedSchemas.joinToString(", ") { "'$it'" }
        val tablesInClause = excludedTables.joinToString(", ") { "'$it'" }
        val query = """
            SELECT table_schema, table_name
              FROM information_schema.tables
             WHERE table_type = 'BASE TABLE'
               AND table_schema NOT IN ($schemasInClause)
               AND table_name NOT IN ($tablesInClause);
        """.trimIndent()

        val tables = mutableListOf<String>()
        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.executeQuery(query).use { resultSet ->
                    while (resultSet.next()) {
                        tables += "${resultSet.getString("table_schema")}.${resultSet.getString("table_name")}"
                    }
                }
            }
        }
        tables.joinToString("\n") { "TRUNCATE $it CASCADE;" }
    }

    fun reset() {
        if (truncateSql.isBlank()) return
        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.execute(truncateSql)
            }
        }
    }
}
