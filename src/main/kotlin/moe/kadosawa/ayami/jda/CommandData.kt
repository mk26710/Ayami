package moe.kadosawa.ayami.jda

import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData

private val PRIVACY_OPTION_DATA =
    OptionData(OptionType.BOOLEAN, "private", "Ayami will respond to you using ephemeral messages", false)

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
    return addSubcommands(SubcommandData(name, description).apply(block))
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