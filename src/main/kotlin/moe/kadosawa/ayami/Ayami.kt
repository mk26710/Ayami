package moe.kadosawa.ayami

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import moe.kadosawa.ayami.commands.SlashCommand
import moe.kadosawa.ayami.listeners.DefaultListener
import mu.KotlinLogging
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent

fun lazyJDA() = lazy {
    JDABuilder
        .createLight(Config.Bot.token)
        .enableIntents(GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES)
        .addEventListeners(DefaultListener())
        .build()
}

object Ayami {
    private val logger = KotlinLogging.logger { }

    /** CoroutineScope using [Dispatchers.Default] */
    val coroutineScope = CoroutineScope(Dispatchers.Default)

    val jda by lazyJDA()
    val commands = mutableMapOf<String, SlashCommand>()

    fun addCommand(command: SlashCommand) {
        commands[command.path] = command
    }
}
