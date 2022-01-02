package moe.kadosawa.ayami.listeners

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moe.kadosawa.ayami.core.Ayami
import moe.kadosawa.ayami.core.Slashes
import mu.KotlinLogging
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MainListener : ListenerAdapter() {
    private val logger = KotlinLogging.logger {}

    private val mainListenerScope = CoroutineScope(Dispatchers.Default)

    override fun onSlashCommand(event: SlashCommandEvent) {
        val cmd = Slashes.executors[event.commandPath]

        if (cmd == null) {
            event.reply("Command Not Found").setEphemeral(true).queue()
            return
        }

        mainListenerScope.launch {
            cmd.run(event)
        }
    }

    override fun onReady(event: ReadyEvent) {
        Ayami.readyDeferred.complete(Unit)
        logger.info { "Ayami's jda is ready!" }
    }
}