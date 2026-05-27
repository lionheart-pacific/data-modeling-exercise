plugins {
    id("convention.kotlin-jvm")
    `java-test-fixtures`
}

dependencies {
    testFixturesApi(libs.junit.jupiter)
    testFixturesApi(libs.strikt.core)
    testFixturesApi(libs.testcontainers.postgresql)
    testFixturesApi(libs.hikariCP)
    testFixturesApi(libs.flyway.databasePostgresql)
    testFixturesRuntimeOnly(libs.postgresql)
}
