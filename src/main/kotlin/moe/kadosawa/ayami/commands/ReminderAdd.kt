package moe.kadosawa.ayami.commands

import kotlinx.datetime.Clock
import moe.kadosawa.ayami.BadArgument
import moe.kadosawa.ayami.abc.SlashCommand
import moe.kadosawa.ayami.database.services.RemindersService
import moe.kadosawa.ayami.database.tables.NewReminder
import moe.kadosawa.ayami.jda.await
import moe.kadosawa.ayami.jda.isPrivate
import moe.kadosawa.ayami.jda.tryDefer
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.utils.TimeFormat
import kotlin.time.Duration

class ReminderAdd : SlashCommand() {
    override val path = "reminder/add"

    override suspend fun invoke(e: SlashCommandEvent) {
        e.tryDefer(e.isPrivate)?.await()

        val duration = e.getOption("duration")!!.asString.let(Duration::parseOrNull)
            ?: throw BadArgument("You have provided invalid duration.")

        val content = e.getOption("content")!!.asString

        val now = Clock.System.now()
        val triggerAt = now.plus(duration)
        val toAdd = NewReminder(triggerAt, e.user.idLong, e.guild?.idLong, e.channel.idLong, content = content)

        val reminder = RemindersService.add(toAdd)
        val triggerAtFormatted = TimeFormat.DATE_TIME_LONG.format(reminder.triggerAt.toEpochMilliseconds())
        e.hook.sendMessage("\uD83D\uDC4C Alright, I'll remind you about this on $triggerAtFormatted (#`${reminder.id}`)").await()
    }
}