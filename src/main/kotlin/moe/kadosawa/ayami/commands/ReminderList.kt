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

import moe.kadosawa.ayami.abc.SlashCommand
import moe.kadosawa.ayami.database.services.RemindersService
import moe.kadosawa.ayami.jda.await
import moe.kadosawa.ayami.jda.dateTimeLong
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent

class ReminderList : SlashCommand("reminder/list") {
    override suspend fun invoke(e: SlashCommandEvent) {
        e.deferReply(true).await()

        val reminders = RemindersService.getUserReminders(e.user.idLong, e.guild?.idLong)
        val joined = reminders
            .joinToString(separator = "\n") { "`#${it.id}` | ${dateTimeLong(it.triggerAt)} | ${it.content.take(50)}" }
            .ifEmpty { "You don't have active reminders." }

        e.hook.sendMessage(joined).await()
    }
}