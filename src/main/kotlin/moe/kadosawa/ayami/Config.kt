package moe.kadosawa.ayami

import mu.KotlinLogging
import java.io.File
import java.util.*
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}

object Config {
    private val properties = Properties().apply {
        setProperty("discord.token", "insert your token here")
        setProperty("prefix.default", "!")
    }

    val discordToken: String
        get() = properties.getProperty("discord.token")

    val defaultPrefix: String
        get() = properties.getProperty("prefix.default")

    fun readFromFile(path: String = "config.properties") {
        val f = File(path)
        if (f.exists()) {
            properties.load(f.reader())
        }

        if (!f.exists()) {
            f.writer().use {
                properties.store(it, "Ayami Config")
                it.close()

                logger.info { "Configuration file did not exist, I have pre-generated a new one for you!" }
                exitProcess(1)
            }

        }
    }
}