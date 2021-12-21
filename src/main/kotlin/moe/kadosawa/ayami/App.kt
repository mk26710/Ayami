package moe.kadosawa.ayami

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import mu.KotlinLogging
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

private val logger = KotlinLogging.logger {}

class Bot : ListenerAdapter() {
    override fun onSlashCommand(event: SlashCommandEvent) {
        when (event.name) {
            "ping" -> {
                event.reply("Pong!").queue()

            }
        }
    }

    override fun onReady(event: ReadyEvent) {
        logger.info { "${event.jda.guilds}" }
    }
}

fun main(args: Array<String>) {
    val parser = ArgParser({}::class.java.packageName)

    val configPath by parser.option(ArgType.String, fullName = "config", shortName = "c")
        .default("config.properties")

    parser.parse(args)
    Config.readFromFile(configPath)

    val jda = JDABuilder.createLight(Config.discordToken)
        .addEventListeners(Bot())
        .build()
}
