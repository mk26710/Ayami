package moe.kadosawa.ayami.utils;

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

private val config = HikariConfig().apply {
    jdbcUrl = "jdbc:pgsql://localhost:5432/ayami"
    driverClassName = "com.impossibl.postgres.jdbc.PGDriver"
    username = "yuzu"
    password = "password"
    maximumPoolSize = 10
}

val dataSource = HikariDataSource(config)
