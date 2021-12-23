package moe.kadosawa.ayami.interfaces

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData

interface Command {
    val data: CommandData

    suspend fun execute(event: SlashCommandEvent)
}