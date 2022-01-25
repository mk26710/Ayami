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

package moe.kadosawa.ayami

import kotlinx.coroutines.runBlocking
import moe.kadosawa.ayami.commands.*
import moe.kadosawa.ayami.database.DatabaseFactory
import mu.KotlinLogging
import kotlin.system.exitProcess

@Suppress("unused")
private val logger = KotlinLogging.logger {}

suspend fun connectDatabase() {
    DatabaseFactory.connect()
    DatabaseFactory.ready.await()
}

fun main(args: Array<String>) = runBlocking {
    parseArgs(args)
    loadProperties(configPath)

    connectDatabase()
    if (dbInit) {
        DatabaseFactory.createTables()
        exitProcess(0)
    }

    Ayami.apply {
        addCommand(PingCommand())
        addCommand(ResinCommand())
        addCommand(CharacterMaterials())
        addCommand(ReminderAdd())
        addCommand(ReminderList())
    }

    Ayami.jda.awaitReady()
    println("$Ayami is ready")
}