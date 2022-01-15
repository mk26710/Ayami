package moe.kadosawa.ayami.discord.core

import kotlinx.coroutines.launch
import moe.kadosawa.ayami.discord.CommandError
import moe.kadosawa.ayami.discord.CommandInvokeError
import mu.KotlinLogging
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MainListener : ListenerAdapter() {
    private val logger = KotlinLogging.logger {}

    override fun onSlashCommand(event: SlashCommandEvent) {
        val cmd = Slashes.executors[event.commandPath]

        if (cmd == null) {
            event.reply("Command Not Found").setEphemeral(true).queue()
            return
        }

        Ayami.listenerScope.launch {
            try {
                cmd.run(event)
            } catch (e: CommandError) {
                handleCommandError(e, event)
            } catch (e: Throwable) {
                handleCommandError(CommandInvokeError(cause = e), event)
            }
        }
    }

    override fun onReady(event: ReadyEvent) {
        Ayami.readyDeferred.complete(Unit)
        logger.info { "Ayami's jda is ready!" }
    }
}