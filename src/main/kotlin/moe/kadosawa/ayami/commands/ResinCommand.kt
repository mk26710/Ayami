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
import moe.kadosawa.ayami.genshin.GenshinUtils
import moe.kadosawa.ayami.jda.await
import moe.kadosawa.ayami.jda.isPrivate
import moe.kadosawa.ayami.jda.tryDefer
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.utils.TimeFormat

class ResinCommand : SlashCommand() {
    override val path = "resin"

    override suspend fun invoke(e: SlashCommandEvent) {
        e.tryDefer(e.isPrivate)?.await()

        val current = e.getOption("current")!!.asLong
        val needed = e.getOption("needed")!!.asLong

        val replenishAt = GenshinUtils.predictResin(current, needed)
        val discordTime = TimeFormat.DATE_TIME_LONG.format(replenishAt.toEpochMilliseconds())

        e.hook.sendMessage("\uD83D\uDC4B You'll have $needed resin on $discordTime").await()
    }
}