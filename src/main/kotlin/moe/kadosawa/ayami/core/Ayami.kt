package moe.kadosawa.ayami.core

import kotlinx.coroutines.*
import moe.kadosawa.ayami.database.DatabaseFactory
import moe.kadosawa.ayami.extensions.*
import moe.kadosawa.ayami.listeners.MainListener
import moe.kadosawa.ayami.database.tables.Reminders
import moe.kadosawa.ayami.utils.Args
import moe.kadosawa.ayami.utils.Config
import mu.KotlinLogging
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.ApplicationInfo
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

    var appInfo by Delegates.notNull<ApplicationInfo>()

    /**
     * Sends list of available slash
     * commands to the Discord API
     */
    suspend fun refreshCommands(refreshGlobal: Boolean = true, refreshDebugGuild: Boolean = true): Boolean {
        jda.awaitReady()

        if (refreshGlobal) {
            jda.updateCommands().addCommands(Slashes.globalData).await()
        }

        if (refreshDebugGuild) {
            jda.getGuildById(Config.debugGuildId)!!.updateCommands().addCommands(Slashes.combinedData).await()
        }

        logger.info { "Global and debug guild commands were re-added!" }
        return true
    }

    suspend fun start() = coroutineScope {
        coroutineScope = this

        DatabaseFactory.connect()
        DatabaseFactory.readyDeferred.await()

        if (Args.dbInit) {
            newSuspendedTransaction {
                if (!Reminders.exists()) {
                    SchemaUtils.create(Reminders)
                }
            }

            logger.info { "Tables were created" }
            exitProcess(0)
        }

        jda.awaitReady()

        appInfo = jda.retrieveApplicationInfo().await()

        if (Args.refreshSlash) {
            launch {
                refreshCommands()
            }
        }
    }
}