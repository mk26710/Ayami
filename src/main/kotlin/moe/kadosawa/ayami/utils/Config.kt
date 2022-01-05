package moe.kadosawa.ayami.utils

import mu.KotlinLogging
import java.io.File
import java.util.*
import kotlin.system.exitProcess

object Config {
    private val logger = KotlinLogging.logger {}

    private val properties = Properties().apply {
        setProperty("bot.token", "insert your token here")
        setProperty("bot.guild", "12312345151")
    }

    object Bot {
        val token: String = properties.getProperty("bot.token")
        val guild: String = properties.getProperty("bot.guild")
    }

    fun load(path: String = "config.properties") {
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