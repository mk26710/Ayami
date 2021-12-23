package moe.kadosawa.ayami.listeners

import kotlinx.coroutines.*
import moe.kadosawa.ayami.commands
import moe.kadosawa.ayami.extensions.await
import moe.kadosawa.ayami.jdaFullyReady
import moe.kadosawa.ayami.utils.Args
import moe.kadosawa.ayami.utils.Config
import mu.KotlinLogging
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

private val logger = KotlinLogging.logger {}

class MainListener : ListenerAdapter() {
    private val mainListenerScope = CoroutineScope(Dispatchers.Default)

    override fun onSlashCommand(event: SlashCommandEvent) {
        val name = event.name
        val cmd = commands[name]

        if (cmd != null) {
            mainListenerScope.launch {
                cmd.execute(event)
            }
        } else {
            event.reply("Command Not Found").setEphemeral(true).queue()
        }
    }

    override fun onReady(event: ReadyEvent) {
        logger.info { "${event.jda.selfUser.name} is ready" }

        if (Args.refreshCommands) {
            mainListenerScope.launch {
                logger.info { "Clearing commands in debug guild" }
                event.jda.getGuildById(Config.debugGuildId)
                    ?.updateCommands()
                    ?.await()

                val commandsData = commands.values.map { it.data }
                event.jda.getGuildById(Config.debugGuildId)
                    ?.updateCommands()
                    ?.addCommands(commandsData)
                    ?.await()
            }
        }

        jdaFullyReady.complete(Unit)
    }
}