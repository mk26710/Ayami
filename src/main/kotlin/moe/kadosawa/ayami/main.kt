@file:JvmName("Main")

package moe.kadosawa.ayami

import dev.minn.jda.ktx.injectKTX
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import moe.kadosawa.ayami.interfaces.Command
import moe.kadosawa.ayami.commands.PingCommand
import moe.kadosawa.ayami.commands.ResinCommand
import moe.kadosawa.ayami.extensions.addCommand
import moe.kadosawa.ayami.listeners.MainListener
import moe.kadosawa.ayami.utils.Args
import moe.kadosawa.ayami.utils.Config
import mu.KotlinLogging
import net.dv8tion.jda.api.JDABuilder

private val logger = KotlinLogging.logger {}

val commands = mutableMapOf<String, Command>()
val jdaFullyReady = CompletableDeferred<Unit>()

val commandsList = mutableSetOf<Command>()

val jda by lazy {
    JDABuilder.createLight(Config.discordToken)
        .injectKTX()
        .addEventListeners(MainListener())
        .build()
}

fun main(args: Array<String>) = runBlocking<Unit> {
    Args.parser.parse(args)
    Config.fromFile(Args.configPath)

    launch { jda.addCommand(PingCommand()) }
    launch { jda.addCommand(ResinCommand()) }
}
