package moe.kadosawa.ayami.listeners

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moe.kadosawa.ayami.commands
import moe.kadosawa.ayami.exceptions.handleException
import moe.kadosawa.ayami.jdaIsReady
import mu.KotlinLogging
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

@Suppress("unused")
private val logger = KotlinLogging.logger {}

class MainListener : ListenerAdapter() {
    private val mainListenerScope = CoroutineScope(Dispatchers.Default)

    override fun onSlashCommand(event: SlashCommandEvent) {
        val name = event.name
        val cmd = commands[name]

        if (cmd != null) {
            mainListenerScope.launch {
                try {
                    cmd.execute(event)
                } catch (ex: Exception) {
                    handleException(ex, event)
                }
            }
        } else {
            event.reply("Command Not Found").setEphemeral(true).queue()
        }
    }

    override fun onReady(event: ReadyEvent) {
        jdaIsReady.complete(Unit)
    }
}