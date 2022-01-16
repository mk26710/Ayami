package moe.kadosawa.ayami

import moe.kadosawa.ayami.jda.await
import moe.kadosawa.ayami.jda.tryDefer
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent

object ExceptionHandler {
    suspend fun handle(event: SlashCommandEvent, ex: AyamiBaseException) {
        event.tryDefer(true)?.await()

        when (ex) {
            is CheckFailure -> {
                event.hook.sendMessage(ex.message ?: "Missing access.").await()
            }

            is BadArgument -> {
                event.hook.sendMessage(ex.message ?: "Invalid command argument.").await()
            }

            is CommandException, is AyamiException -> {
                event.hook.sendMessage(ex.message ?: "Unknown error.").await()
            }
        }
    }
}

