package moe.kadosawa.ayami

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import moe.kadosawa.ayami.commands.handlePing
import moe.kadosawa.ayami.extensions.addCommand
import moe.kadosawa.ayami.listeners.MainListener
import mu.KotlinLogging
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent

@Suppress("unused") private val logger = KotlinLogging.logger {}

val commands = mutableMapOf<String, (event: SlashCommandEvent) -> Any>()

val jda by lazy {
    JDABuilder.createLight(Config.discordToken)
        .addEventListeners(MainListener())
        .build()
}

fun main(args: Array<String>) {
    val parser = ArgParser("Ayami")

    val configPath by parser.option(ArgType.String, fullName = "config", shortName = "c")
        .default("config.properties")

    parser.parse(args)
    Config.readFromFile(configPath)

    jda.addCommand("ping", "sends pong", ::handlePing)
}
