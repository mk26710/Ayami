package moe.kadosawa.ayami

import kotlinx.coroutines.runBlocking
import moe.kadosawa.ayami.commands.CharacterMaterials
import moe.kadosawa.ayami.commands.PingCommand
import moe.kadosawa.ayami.commands.ResinCommand
import moe.kadosawa.ayami.database.DatabaseFactory
import moe.kadosawa.ayami.genshin.Genshin
import mu.KotlinLogging
import kotlin.system.exitProcess

@Suppress("unused")
private val logger = KotlinLogging.logger {}

suspend fun connectDatabase() {
    DatabaseFactory.connect()
    DatabaseFactory.ready.await()
}

fun main(args: Array<String>) = runBlocking {
    parseArgs(args)
    loadProperties(configPath)

    connectDatabase()
    if (dbInit) {
        DatabaseFactory.createTables()
        exitProcess(0)
    }

    Ayami.apply {
        addCommand(PingCommand())
        addCommand(ResinCommand())
        addCommand(CharacterMaterials())
    }

    Ayami.jda.awaitReady()
    println("$Ayami is ready")
}