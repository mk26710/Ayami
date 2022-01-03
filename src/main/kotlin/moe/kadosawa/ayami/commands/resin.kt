package moe.kadosawa.ayami.commands

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus
import moe.kadosawa.ayami.errors.BadArgument
import moe.kadosawa.ayami.extensions.await
import moe.kadosawa.ayami.extensions.isPrivate
import moe.kadosawa.ayami.interfaces.SlashExecutor
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent

class ResinSlash : SlashExecutor() {
    override val path = "resin"

    override suspend fun invoke(event: SlashCommandEvent) {
        event.deferReply(event.isPrivate).await()

        val current = event.getOption("current")!!.asLong
        val needed = event.getOption("needed")!!.asLong

        if (needed < current || needed == current) {
            throw BadArgument("Your **needed** amount must be greater than **current**!")
        }

        val deltaMinutes = (needed - current) * 8
        val now = Clock.System.now()
        val then = now.plus(deltaMinutes, DateTimeUnit.MINUTE)

        val fullTimestamp = "<t:${then.epochSeconds}:F>"
        val relativeTimestamp = "<t:${then.epochSeconds}:R>"

        event.hook.sendMessage("You will have **$needed** resin at $fullTimestamp ($relativeTimestamp)").await()
    }
}