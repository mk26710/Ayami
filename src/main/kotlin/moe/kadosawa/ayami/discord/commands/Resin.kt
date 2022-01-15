package moe.kadosawa.ayami.discord.commands

import moe.kadosawa.ayami.discord.BadArgument
import moe.kadosawa.ayami.extensions.await
import moe.kadosawa.ayami.extensions.isPrivate
import moe.kadosawa.ayami.genshin.GenshinUtils
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.utils.TimeFormat

object Resin : BaseSlash() {
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

        val dateTimeShort = TimeFormat.DATE_TIME_SHORT.format(refillsAt.toEpochMilliseconds())
        val timeRelative = TimeFormat.RELATIVE.format(refillsAt.toEpochMilliseconds())

        event.hook.sendMessage("**$needed** resin will be replenished at $dateTimeShort ($timeRelative)").await()
    }
}