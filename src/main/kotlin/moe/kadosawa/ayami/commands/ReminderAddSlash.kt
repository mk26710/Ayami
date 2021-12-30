package moe.kadosawa.ayami.commands

import dev.minn.jda.ktx.interactions.Command
import dev.minn.jda.ktx.interactions.option
import kotlinx.datetime.Clock
import moe.kadosawa.ayami.extensions.await
import moe.kadosawa.ayami.interfaces.SlashExecutor
import moe.kadosawa.ayami.tables.Reminders
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.time.Duration

class ReminderAddSlash : SlashExecutor() {
    override val data = Command("reminder-add", "Creates a reminder") {
        option<String>("duration", "ISO-8601 duration format", true)
        option<String>("content", "Message that you will receive", true)
        option<Boolean>("private", "Choose whether you want response to be seen by everyone or not")
    }

    override suspend fun execute(event: SlashCommandEvent) {
        event.deferReply(isPrivate(event)).await()
        // Create an Instant before doing anything
        // in order to make things a bit more accurate
        val now = Clock.System.now()

        val durationToParse = event.getOption("duration")!!.asString
        val userContent = event.getOption("content")!!.asString

        val duration = Duration.parseOrNull(durationToParse)
        if (duration == null) {
            event.hook.sendMessage("You have provided an incorrect duration syntax!").await()
            return
        }

        val toTriggerAt = now + duration
        val reminderId = newSuspendedTransaction {
            Reminders.insert {
                it[triggerAt] = toTriggerAt
                it[authorId] = event.user.idLong
                it[guildId] = event.guild?.idLong
                it[channelId] = event.channel.idLong
                it[content] = userContent
            } get Reminders.id
        }

        event.hook.sendMessage("Reminder `#$reminderId` was created!").await()
    }
}