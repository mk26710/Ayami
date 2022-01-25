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

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import moe.kadosawa.ayami.abc.SlashCommand
import moe.kadosawa.ayami.jda.ui.ButtonCallback
import moe.kadosawa.ayami.listeners.DefaultListener
import moe.kadosawa.ayami.listeners.UIListener
import mu.KotlinLogging
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import java.util.concurrent.ConcurrentHashMap

object Ayami {
    @Suppress("unused")
    private val logger = KotlinLogging.logger { }

    private val intents = listOf(GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES)

    val jda by lazy {
        JDABuilder
            .createLight(Config.Bot.token)
            .enableIntents(intents)
            .addEventListeners(DefaultListener())
            .addEventListeners(UIListener())
            .build()
    }

    val defaultScope = CoroutineScope(Dispatchers.Default)
    val cleanupScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    val commands = ConcurrentHashMap<String, SlashCommand>()
    val buttons = ConcurrentHashMap<String, ButtonCallback>()

    fun addCommand(command: SlashCommand) {
        commands[command.path] = command
    }
}

