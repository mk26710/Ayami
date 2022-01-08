package moe.kadosawa.ayami.discord.commands

import moe.kadosawa.ayami.discord.errors.BadArgument
import moe.kadosawa.ayami.discord.extensions.await
import moe.kadosawa.ayami.discord.extensions.isPrivate
import moe.kadosawa.ayami.discord.interfaces.SlashExecutor
import moe.kadosawa.ayami.genshin.GenshinUtils
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.utils.TimeFormat

object ResinSlash : SlashExecutor() {
    override val path = "resin"

    override suspend fun invoke(event: SlashCommandEvent) {
        event.deferReply(event.isPrivate).await()

        val current = event.getOption("current")!!.asLong
        val needed = event.getOption("needed")!!.asLong

        val refillsAt = try {
            GenshinUtils.resinRefill(needed, current)
        } catch (e: IllegalArgumentException) {
            throw BadArgument(e.message)
        }

        val fullTimestamp = TimeFormat.DATE_TIME_LONG.format(refillsAt.toEpochMilliseconds())
        val relativeTimestamp = TimeFormat.RELATIVE.format(refillsAt.toEpochMilliseconds())

        event.hook.sendMessage("You will have **$needed** resin at $fullTimestamp ($relativeTimestamp)").await()
    }
}