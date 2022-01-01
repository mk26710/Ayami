package moe.kadosawa.ayami.listeners

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moe.kadosawa.ayami.core.Ayami
import moe.kadosawa.ayami.core.Slashes
import moe.kadosawa.ayami.exceptions.handleException
import mu.KotlinLogging
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

@Suppress("unused")
private val logger = KotlinLogging.logger {}

class MainListener : ListenerAdapter() {
    private val mainListenerScope = CoroutineScope(Dispatchers.Default)

    override fun onSlashCommand(event: SlashCommandEvent) {
        val cmd = Slashes.executors[event.commandPath]

        if (cmd != null) {
            mainListenerScope.launch {
                logger.debug { "Launching ${cmd.path}..." }
                try {
                    cmd.execute(event)
                    logger.debug { "${cmd.path} finished" }
                } catch (ex: Exception) {
                    handleException(ex, event)
                    logger.debug { "${cmd.path} finished with an exception" }
                }
            }
        } else {
            event.reply("Command Not Found").setEphemeral(true).queue()
        }
    }

    override fun onReady(event: ReadyEvent) {
        Ayami.readyDeferred.complete(Unit)
        logger.info { "Ayami's jda is ready!" }
    }
}