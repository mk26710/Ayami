package moe.kadosawa.ayami

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import moe.kadosawa.ayami.abc.SlashCommand
import moe.kadosawa.ayami.jda.ui.ButtonCallback
import moe.kadosawa.ayami.listeners.DefaultListener
import moe.kadosawa.ayami.listeners.UIListener
import mu.KotlinLogging
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import java.util.concurrent.ConcurrentHashMap

object Ayami {
    @Suppress("unused")
    private val logger = KotlinLogging.logger { }

    private val intents = listOf(GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES)

    val jda by lazy {
        JDABuilder
            .createLight(Config.Bot.token)
            .enableIntents(intents)
            .addEventListeners(DefaultListener())
            .addEventListeners(UIListener())
            .build()
    }

    val defaultScope = CoroutineScope(Dispatchers.Default)
    val cleanupScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    val commands = ConcurrentHashMap<String, SlashCommand>()
    val buttons = ConcurrentHashMap<String, ButtonCallback>()

    fun addCommand(command: SlashCommand) {
        commands[command.path] = command
    }
}

