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