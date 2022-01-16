package moe.kadosawa.ayami.jda

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyAction

/**
 * Tells if user wants an ephemeral response
 */
val SlashCommandEvent.isPrivate
    get() = getOption("private")?.asBoolean ?: false

/**
 * Acknowledge interaction if it wasn't acknowledged
 */
fun SlashCommandEvent.tryDefer(ephemeral: Boolean = false): ReplyAction? {
    if (isAcknowledged) {
        return null
    }

    return deferReply(ephemeral)
}