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

import kotlinx.datetime.Clock
import moe.kadosawa.ayami.exceptions.BadArgument
import moe.kadosawa.ayami.abc.SlashCommand
import moe.kadosawa.ayami.database.services.RemindersService
import moe.kadosawa.ayami.database.tables.NewReminder
import moe.kadosawa.ayami.jda.await
import moe.kadosawa.ayami.jda.dateTimeLong
import moe.kadosawa.ayami.jda.isPrivate
import moe.kadosawa.ayami.jda.tryDefer
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import kotlin.time.Duration

class ReminderAdd : SlashCommand("reminder/add") {
    override suspend fun invoke(e: SlashCommandEvent) {
        e.tryDefer(e.isPrivate)?.await()

        val duration = e.getOption("duration")!!.asString.let(Duration::parseOrNull)
            ?: throw BadArgument("You have provided invalid duration.")

        val content = e.getOption("content")!!.asString

        val now = Clock.System.now()
        val triggerAt = now.plus(duration)
        val toAdd = NewReminder(triggerAt, e.user.idLong, e.guild?.idLong, e.channel.idLong, content = content)

        val reminder = RemindersService.add(toAdd)
        val triggerAtFormatted = dateTimeLong(reminder.triggerAt)
        e.hook.sendMessage("\uD83D\uDC4C Alright, I'll remind you about this on $triggerAtFormatted (#`${reminder.id}`)").await()
    }
}