package moe.kadosawa.ayami.listeners

import moe.kadosawa.ayami.commands
import mu.KotlinLogging
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

private val logger = KotlinLogging.logger {}

class MainListener : ListenerAdapter() {
    override fun onSlashCommand(event: SlashCommandEvent) {
        val name = event.name
        val execute = commands[name]

        if (execute != null) {
            execute(event)
        } else {
            event.reply("Command Not Found").setEphemeral(true).queue()
        }
    }

    override fun onReady(event: ReadyEvent) {
        logger.info { "Ayami is ready!" }
    }
}