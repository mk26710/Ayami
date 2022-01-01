package moe.kadosawa.ayami.core

import kotlinx.coroutines.*
import moe.kadosawa.ayami.extensions.*
import moe.kadosawa.ayami.listeners.MainListener
import moe.kadosawa.ayami.tables.Reminders
import moe.kadosawa.ayami.utils.Args
import moe.kadosawa.ayami.utils.Config
import moe.kadosawa.ayami.utils.MyDatabase
import mu.KotlinLogging
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.exists
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.properties.Delegates
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}

object Ayami {
    var coroutineScope: CoroutineScope by Delegates.notNull()

    val readyDeferred = CompletableDeferred<Unit>()

    val jda by lazy {
        JDABuilder.createLight(Config.discordToken)
            .addEventListeners(MainListener())
            .enableIntents(
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.DIRECT_MESSAGES,
                GatewayIntent.GUILD_MESSAGES
            )
            .build()
    }

    /**
     * Sends list of available slash
     * commands to the Discord API
     */
    suspend fun refreshCommands(refreshGlobal: Boolean = true, refreshDebugGuild: Boolean = true) {
        jda.awaitReady()

        if (refreshGlobal) {
            jda.updateCommands().addCommands(Slashes.data).await()
        }

        if (refreshDebugGuild) {
            jda.getGuildById("911222786968674334")!!.updateCommands().addCommands(Slashes.data).await()
        }

        logger.info { "Global and debug guild commands were re-added!" }
    }

    suspend fun start() = coroutineScope {
        coroutineScope = this

        if (Args.refreshSlash) {
            launch {
                refreshCommands()
            }
        }

        MyDatabase.connect()
        MyDatabase.readyDeferred.await()

        if (Args.dbInit) {
            newSuspendedTransaction {
                if (!Reminders.exists()) {
                    SchemaUtils.create(Reminders)
                }
            }

            logger.info { "Tables were created" }
            exitProcess(0)
        }
    }
}