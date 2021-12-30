package moe.kadosawa.ayami.extensions

import kotlinx.coroutines.CompletableDeferred
import mu.KotlinLogging
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import net.dv8tion.jda.api.requests.RestAction

@Suppress("unused")
private val logger = KotlinLogging.logger {}

private val PRIVACY_OPTION_DATA =
    OptionData(OptionType.BOOLEAN, "private", "Ayami will respond to you using ephemeral messages", false)


/**
 * Wraps [RestAction.queue] into [CompletableDeferred]
 */
suspend fun <T : Any> RestAction<T>.await(): T {
    val result = CompletableDeferred<T>()

    queue {
        result.complete(it)
    }

    return result.await()
}

/**
 * Adds ``private`` option to the command
 */
fun CommandData.privacyOption(): CommandData {
    return addOptions(PRIVACY_OPTION_DATA)
}

/**
 * Adds ``private`` option to the subcommand
 */
fun SubcommandData.privacyOption(): SubcommandData {
    return addOptions(PRIVACY_OPTION_DATA)
}

/**
 * Tells if user wants an ephemeral response
 */
val SlashCommandEvent.isPrivate
    get() = getOption("private")?.asBoolean ?: false

/*
 * Kotlin-style builders
 */

fun command(name: String, description: String, block: CommandData.() -> Unit = {}): CommandData {
    return CommandData(name, description).apply(block)
}

fun subcommandData(name: String, description: String, block: SubcommandData.() -> Unit = {}): SubcommandData {
    return SubcommandData(name, description).apply(block)
}

fun CommandData.subcommandData(name: String, description: String, block: SubcommandData.() -> Unit = {}): CommandData {
    return addSubcommands(moe.kadosawa.ayami.extensions.subcommandData(name, description, block))
}

fun CommandData.option(
    type: OptionType,
    name: String,
    description: String,
    required: Boolean = true,
    block: OptionData.() -> Unit = {}
): CommandData {
    return addOptions(OptionData(type, name, description, required).apply(block))
}

fun SubcommandData.option(
    type: OptionType,
    name: String,
    description: String,
    required: Boolean = true,
    block: OptionData.() -> Unit = {}
): SubcommandData {
    return addOptions(OptionData(type, name, description, required).apply(block))
}
