package moe.kadosawa.ayami

import kotlinx.coroutines.runBlocking
import moe.kadosawa.ayami.discord.core.Ayami
import moe.kadosawa.ayami.utils.Args
import moe.kadosawa.ayami.utils.Config
import mu.KotlinLogging


@Suppress("unused")
private val logger = KotlinLogging.logger {}

@Suppress("RemoveExplicitTypeArguments")
fun main(args: Array<String>) = runBlocking<Unit> {
    Args.parser.parse(args)
    Config.load(Args.configPath)

    Ayami.start()
}
