package moe.kadosawa.ayami.extensions

import kotlinx.coroutines.CompletableDeferred
import mu.KotlinLogging
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.requests.RestAction

@Suppress("unused")
private val logger = KotlinLogging.logger {}

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
