package moe.kadosawa.ayami.interfaces

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent

abstract class SlashExecutor {
    abstract val path: String

    abstract suspend fun execute(event: SlashCommandEvent)
}