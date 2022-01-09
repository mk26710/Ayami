package moe.kadosawa.ayami.discord.commands

import kotlinx.datetime.Clock
import moe.kadosawa.ayami.database.services.RemindersService
import moe.kadosawa.ayami.database.tables.NewReminder
import moe.kadosawa.ayami.extensions.await
import moe.kadosawa.ayami.extensions.isPrivate
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import kotlin.time.Duration

object ReminderAdd : BaseSlash() {
    override val path = "reminder/add"

    override suspend fun invoke(event: SlashCommandEvent) {
        event.deferReply(event.isPrivate).await()
        // Create an Instant before doing anything
        // in order to make things a bit more accurate
        val now = Clock.System.now()

        val unparsedDuration = event.getOption("duration")!!.asString
        val content = event.getOption("content")!!.asString

        // Try to parse user's duration as Duration
        val duration = Duration.parseOrNull(unparsedDuration)
        if (duration == null) {
            event.hook.sendMessage("You have provided an incorrect duration syntax!").await()
            return
        }

        val toTriggerAt = now + duration

        val newReminder = NewReminder(
            triggerAt = toTriggerAt,
            authorId = event.user.idLong,
            guildId = event.guild?.idLong,
            channelId = event.channel.idLong,
            content = content
        )

        val reminder = RemindersService.add(newReminder)
        val formattedTime = "<t:${reminder.triggerAt.epochSeconds}:R>"
        event.hook.sendMessage(":ok_hand: I will remind you about this $formattedTime (`#${reminder.id}`)").await()
    }
}