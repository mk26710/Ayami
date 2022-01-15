package moe.kadosawa.ayami.discord.core

import moe.kadosawa.ayami.discord.BadArgument
import moe.kadosawa.ayami.discord.CheckFailure
import moe.kadosawa.ayami.discord.CommandError
import moe.kadosawa.ayami.discord.CommandInvokeError
import moe.kadosawa.ayami.extensions.await
import mu.KotlinLogging
import net.dv8tion.jda.api.interactions.commands.CommandInteraction


private val logger = KotlinLogging.logger {}

suspend fun handleCommandError(e: CommandError, context: CommandInteraction) {
    if (!context.isAcknowledged) {
        context.deferReply(true).await()
    }

    when (e) {
        is CommandInvokeError -> {
            logger.error(e) { "Unhandled error during command invocation" }
            context.hook.sendMessage("Something went wrong..").await()
        }

        is CheckFailure -> {
            val msg = e.message ?: "You can't run this command."
            context.hook.sendMessage(msg).await()
        }

        is BadArgument -> {
            val msg = e.message ?: "You have provided an invalid input."
            context.hook.sendMessage(msg).await()
        }
    }
}
