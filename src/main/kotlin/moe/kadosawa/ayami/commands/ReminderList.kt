package moe.kadosawa.ayami.commands

import moe.kadosawa.ayami.abc.SlashCommand
import moe.kadosawa.ayami.database.services.RemindersService
import moe.kadosawa.ayami.jda.await
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent

// TODO: Paginated reply with buttons
class ReminderList : SlashCommand() {
    override val path = "reminder/list"

    override suspend fun invoke(e: SlashCommandEvent) {
        e.deferReply(true).await()

        val reminders = RemindersService.getUserReminders(e.user.idLong, e.guild?.idLong)
        val joined = reminders.joinToString(separator = "\n") {
            "`#${it.id}` | <t:${it.triggerAt.epochSeconds}:F> | ${it.content.take(50)}"
        }

        e.hook.sendMessage(joined).await()
    }
}