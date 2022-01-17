package moe.kadosawa.ayami.listeners

import kotlinx.coroutines.launch
import moe.kadosawa.ayami.Ayami
import moe.kadosawa.ayami.AyamiBaseException
import moe.kadosawa.ayami.CommandException
import moe.kadosawa.ayami.ExceptionHandler
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class DefaultListener : ListenerAdapter() {
    override fun onSlashCommand(event: SlashCommandEvent) {
        val command = Ayami.commands[event.commandPath]
        if (command == null) {
            event.reply("Command not found.").setEphemeral(true).queue()
            return
        }

        Ayami.coroutineScope.launch {
            try {
                command.run(event)
            } catch (ex: AyamiBaseException) {
                ExceptionHandler.handle(event, ex)
            } catch (ex: Throwable) {
                ExceptionHandler.handle(event, CommandException(cause = ex))
            }
        }
    }
}