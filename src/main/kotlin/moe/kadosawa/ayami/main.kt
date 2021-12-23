@file:JvmName("Main")

package moe.kadosawa.ayami

import dev.minn.jda.ktx.await
import dev.minn.jda.ktx.intents
import dev.minn.jda.ktx.light
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import moe.kadosawa.ayami.interfaces.Command
import moe.kadosawa.ayami.commands.PingCommand
import moe.kadosawa.ayami.commands.ResinCommand
import moe.kadosawa.ayami.listeners.MainListener
import moe.kadosawa.ayami.utils.Args
import moe.kadosawa.ayami.utils.Config
import mu.KotlinLogging
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.requests.GatewayIntent
import kotlin.properties.Delegates

private val logger = KotlinLogging.logger {}

val jdaIsReady = CompletableDeferred<Unit>()

var commands: MutableMap<String, Command> by Delegates.notNull()
var jda: JDA by Delegates.notNull()

private suspend fun onceReady() {
    // Wait for the ready event
    jdaIsReady.await()

    if (Args.refreshSlash) {
        val commandsData = commands.values.map { cmd -> cmd.data }
        // Re-add the commands
        jda.updateCommands()
            .addCommands(commandsData)
            .await()

        jda.getGuildById("911222786968674334")!!
            .updateCommands()
            .addCommands(commandsData)
            .await()

        logger.info { "Global and debug guild commands were re-added!" }
    }
}

fun main(args: Array<String>) = runBlocking<Unit> {
    Args.parser.parse(args)
    Config.fromFile(Args.configPath)

    commands = mutableMapOf(
        "ping" to PingCommand(),
        "resin" to ResinCommand()
    )

    jda = light(Config.discordToken) {
        intents += GatewayIntent.GUILD_MEMBERS
        intents += GatewayIntent.DIRECT_MESSAGES
        intents += GatewayIntent.GUILD_MESSAGES

        addEventListeners(MainListener())
    }

    launch { onceReady() }
}
