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

package moe.kadosawa.ayami.listeners

import kotlinx.coroutines.launch
import moe.kadosawa.ayami.Ayami
import moe.kadosawa.ayami.exceptions.AyamiBaseException
import moe.kadosawa.ayami.exceptions.CommandException
import moe.kadosawa.ayami.exceptions.ExceptionHandler
import moe.kadosawa.ayami.jda.await
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class DefaultListener : ListenerAdapter() {
    override fun onReady(event: ReadyEvent) {
        Ayami.defaultScope.launch {
            Ayami.appInfo = Ayami.jda.retrieveApplicationInfo().await()
        }
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val command = Ayami.commands[event.commandPath]
        if (command == null) {
            event.reply("Command not found.").setEphemeral(true).queue()
            return
        }

        Ayami.defaultScope.launch {
            try {
                command.run(event)
            } catch (ex: AyamiBaseException) {
                ExceptionHandler.handle(event, ex)
            } catch (ex: Throwable) {
                ex.printStackTrace()
                ExceptionHandler.handle(event, CommandException(cause = ex))
            }
        }
    }
}