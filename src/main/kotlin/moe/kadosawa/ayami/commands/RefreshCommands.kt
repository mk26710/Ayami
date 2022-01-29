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

package moe.kadosawa.ayami.commands

import moe.kadosawa.ayami.Ayami
import moe.kadosawa.ayami.exceptions.BadArgument
import moe.kadosawa.ayami.abc.SlashCommand
import moe.kadosawa.ayami.jda.await
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class RefreshCommands : SlashCommand("refresh-commands") {
    override suspend fun check(e: SlashCommandInteractionEvent) =
        e.user.id == Ayami.appInfo.owner.id

    override suspend fun invoke(e: SlashCommandInteractionEvent) {
        e.deferReply().await()

        val global = e.getOption("global")!!.asBoolean
        val debug = e.getOption("debug")!!.asBoolean

        if (!global && !debug) {
            throw BadArgument("What do you expect? Both arguments are false, nothing was refreshed.")
        }

        Ayami.refreshCommands(global, debug)
        e.hook.sendMessage("Commands update request was sent").await()
    }
}