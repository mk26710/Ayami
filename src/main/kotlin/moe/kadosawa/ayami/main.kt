package moe.kadosawa.ayami

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import moe.kadosawa.ayami.database.DatabaseFactory
import moe.kadosawa.ayami.database.tables.Reminders
import moe.kadosawa.ayami.discord.core.Ayami
import moe.kadosawa.ayami.extensions.await
import moe.kadosawa.ayami.utils.Args
import moe.kadosawa.ayami.utils.Config
import mu.KotlinLogging
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.exists
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.system.exitProcess


@Suppress("unused")
private val logger = KotlinLogging.logger {}

@Suppress("RemoveExplicitTypeArguments")
fun main(args: Array<String>) = runBlocking<Unit> {
    Args.parser.parse(args)
    Config.load(Args.configPath)

    connectDatabase()
    if (Args.dbInit) {
        dbInit()
    }

    connectAyami()
}

private suspend fun connectDatabase() {
    DatabaseFactory.connect()
    DatabaseFactory.readyDeferred.await()
}

private suspend fun dbInit() {
    newSuspendedTransaction {
        if (!Reminders.exists()) {
            SchemaUtils.create(Reminders)
        }
    }

    logger.info { "Tables were created" }
    exitProcess(0)
}

private suspend fun connectAyami() = coroutineScope {
    Ayami.jda.awaitReady()
    Ayami.appInfo = Ayami.jda.retrieveApplicationInfo().await()

    if (Args.refreshSlash) {
        launch {
            Ayami.refreshCommands()
        }
    }
}
