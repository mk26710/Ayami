package moe.kadosawa.ayami.extensions

import kotlinx.coroutines.CompletableDeferred
import moe.kadosawa.ayami.commands
import moe.kadosawa.ayami.commandsList
import moe.kadosawa.ayami.interfaces.Command
import moe.kadosawa.ayami.jdaFullyReady
import moe.kadosawa.ayami.utils.Args
import mu.KotlinLogging
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.requests.RestAction

private val logger = KotlinLogging.logger {}

suspend fun <T : Any> RestAction<T>.await(): T {
    val result = CompletableDeferred<T>()

    queue {
        result.complete(it)
    }

    return result.await()
}

suspend fun JDA.addCommand(command: Command) {
    commandsList.add(command)
    logger.info { "${command.data.name} was added" }

    if (Args.refreshCommands) {
        jdaFullyReady.await()
        upsertCommand(command.data).queue()
        logger.info { "upsert request for ${command.data.name} to global commands list was created" }
    }
}

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
