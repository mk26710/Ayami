/*
 * Copyright 2022 kadosawa (kadosawa.moe)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package moe.kadosawa.ayami.utils

import mu.KotlinLogging
import java.io.File
import java.util.*
import kotlin.system.exitProcess


private val logger = KotlinLogging.logger {}

private val properties = Properties().apply {
    setProperty("bot.token", "insert your token here")
    setProperty("bot.guild", "12312345151")
    setProperty("hikari.host", "localhost")
    setProperty("hikari.port", "5432")
    setProperty("hikari.database", "<database name>")
    setProperty("hikari.username", "<database owner username>")
    setProperty("hikari.password", "<user password>")
}

object Config {
    object Bot {
        val token = property("bot.token")
        val guild = property("bot.guild")
    }

    object Hikari {
        val host = property("hikari.host")
        val port = property("hikari.port")
        val database = property("hikari.database")
        val username = property("hikari.username")
        val password = property("hikari.password")

        fun jdbcUrl() = "jdbc:postgresql://$host:$port/$database"
    }
}

fun property(key: String): String = properties.getProperty(key)

fun loadProperties(path: String = "config.properties") {
    val f = File(path)
    if (!f.exists()) {
        f.writer().use {
            properties.store(it, "Ayami Config")
            it.close()

            logger.info { "Configuration file did not exist, I have pre-generated a new one for you!" }
            exitProcess(1)
        }
    }

    properties.load(f.reader())
}
