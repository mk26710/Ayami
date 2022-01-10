package moe.kadosawa.ayami.discord.commands

import moe.kadosawa.ayami.database.services.RemindersService
import moe.kadosawa.ayami.extensions.await
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.utils.TimeFormat

// TODO: Pagination would work nicely here
object ReminderList : BaseSlash() {
    override val path = "reminder/list"

    override suspend fun invoke(event: SlashCommandEvent) {
        event.deferReply(true).await()

        val reminders = RemindersService.getUserReminders(event.user.idLong, event.guild?.idLong)
        if (reminders.isEmpty()) {
            event.hook.sendMessage("You don't have any active reminders.")
            return
        }

        val msg = reminders.joinToString(separator = "\n") {
            val triggerTime = TimeFormat.DATE_TIME_LONG.format(it.triggerAt.toEpochMilliseconds())
            "**#${it.id}** (${triggerTime}): ${it.content}"
        }

        event.hook.sendMessage("Your active reminders:\n${msg}").await()
    }
}