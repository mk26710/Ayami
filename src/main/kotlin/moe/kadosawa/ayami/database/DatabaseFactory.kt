package moe.kadosawa.ayami.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.CompletableDeferred
import moe.kadosawa.ayami.utils.Config
import mu.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.properties.Delegates

private val logger = KotlinLogging.logger {}

object DatabaseFactory {
    var database by Delegates.notNull<Database>()
    val readyDeferred = CompletableDeferred<Unit>()

    val hikariConfig = HikariConfig().apply {
        jdbcUrl = Config.Hikari.jdbcUrl()
        username = Config.Hikari.user
        password = Config.Hikari.password
        maximumPoolSize = 10
    }

    val hikariSource = HikariDataSource(hikariConfig)

    suspend fun connect() {
        database = Database.connect(hikariSource)

        newSuspendedTransaction {
            exec("SELECT 1;")
            readyDeferred.complete(Unit)
            logger.info { "Database connection established" }
        }
    }
}

