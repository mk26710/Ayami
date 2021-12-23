package moe.kadosawa.ayami.exceptions

import moe.kadosawa.ayami.extensions.await
import mu.KotlinLogging
import net.dv8tion.jda.api.interactions.commands.CommandInteraction

private val logger = KotlinLogging.logger {}

suspend fun handleException(ex: Exception, context: CommandInteraction) {
    val stackTrace = ex.stackTrace.joinToString("\n", prefix = "\n", postfix = "\n")

    if (!context.isAcknowledged) {
        context.deferReply()
    }

    when (ex) {
        is InvalidInteractionOption -> {
            val msg = ex.message ?: "You have provided an invalid input."
            context.hook.sendMessage(msg).await()

            logger.warn {
                "${ex::class.qualifiedName} was caught and handled:${stackTrace}"
            }
        }

        else -> {
            logger.error(ex) { "UNHANDLED EXCEPTION WAS CAUGHT" }
            context.hook.sendMessage("Something went wrong...").await()
        }
    }
}