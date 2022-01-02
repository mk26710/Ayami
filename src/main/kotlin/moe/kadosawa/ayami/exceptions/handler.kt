package moe.kadosawa.ayami.exceptions

import moe.kadosawa.ayami.extensions.await
import mu.KotlinLogging
import net.dv8tion.jda.api.interactions.commands.CommandInteraction

@Suppress("unused")
private val logger = KotlinLogging.logger {}

suspend fun handleCommandError(e: CommandError, context: CommandInteraction) {
    if (!context.isAcknowledged) {
        context.deferReply(true)
    }

    when (e) {
        is CommandInvokeError -> {
            logger.error(e) { "Unhandled error during command invocation" }
            context.hook.sendMessage("Something went wrong..").await()
        }

        is BadArgument -> {
            val msg = e.message ?: "You have provided an invalid input."
            context.hook.sendMessage(msg).await()
        }
    }
}