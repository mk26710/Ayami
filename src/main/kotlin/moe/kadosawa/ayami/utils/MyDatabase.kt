package moe.kadosawa.ayami.utils

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.CompletableDeferred
import mu.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

private val logger = KotlinLogging.logger {}

object MyDatabase {
    val readyDeferred = CompletableDeferred<Unit>()

    val hikariConfig = HikariConfig().apply {
        jdbcUrl = "jdbc:postgresql://localhost:5432/ayami"
        username = "yuzu"
        password = "password"
        maximumPoolSize = 10
    }

    val hikariSource = HikariDataSource(hikariConfig)

    suspend fun connect() {
        Database.connect(hikariSource)

        newSuspendedTransaction {
            exec("SELECT 1;")
            readyDeferred.complete(Unit)
            logger.info { "Database connection established" }
        }
    }
}

