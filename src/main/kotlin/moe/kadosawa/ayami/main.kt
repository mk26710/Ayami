package moe.kadosawa.ayami

import kotlinx.coroutines.runBlocking
import moe.kadosawa.ayami.core.Ayami
import moe.kadosawa.ayami.utils.Args
import moe.kadosawa.ayami.utils.Config
import mu.KotlinLogging


private val logger = KotlinLogging.logger {}

@Suppress("RemoveExplicitTypeArguments")
fun main(args: Array<String>) = runBlocking<Unit> {
    Args.parser.parse(args)
    Config.fromFile(Args.configPath)

    Ayami.start()
}
