package moe.kadosawa.ayami.utils;

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

private val config = HikariConfig().apply {
    jdbcUrl = "jdbc:postgresql://localhost:5432/ayami"
    username = "yuzu"
    password = "password"
    maximumPoolSize = 10
}

val dataSource = HikariDataSource(config)
