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

//fun commandData(name: String, description: String, init: CommandData.() -> Unit): CommandData =
//    CommandData(name, description).apply(init)
//
//fun CommandData.option(type: OptionType, name: String, description: String, required: Boolean = true, init: OptionData.() -> Unit): CommandData =
//    this.addOptions(OptionData(type, name, description, required).apply(init))
//
//
//fun OptionData.choice(name: String, value: Double) = addChoice(name, value)
//fun OptionData.choice(name: String, value: Long) = addChoice(name, value)
//fun OptionData.choice(name: String, value: String) = addChoice(name, value)
