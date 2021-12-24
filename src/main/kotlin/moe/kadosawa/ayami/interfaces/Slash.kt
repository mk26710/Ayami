package moe.kadosawa.ayami.interfaces

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData

abstract class Slash {
    abstract val data: CommandData

    fun isPrivate(event: SlashCommandEvent): Boolean {
        return event.getOption("private")?.asBoolean ?: false
    }

    abstract suspend fun execute(event: SlashCommandEvent)
}