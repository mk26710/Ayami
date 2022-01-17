package moe.kadosawa.ayami.abc

import moe.kadosawa.ayami.CheckFailure
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent

abstract class SlashCommand {
    abstract val path: String

    open suspend fun check(e: SlashCommandEvent): Boolean = true

    abstract suspend fun invoke(e: SlashCommandEvent)

    suspend fun run(e: SlashCommandEvent) {
        val canRun = check(e)
        if (!canRun) {
            throw CheckFailure("You don't have access to this command, sorry.")
        }

        invoke(e)
    }
}