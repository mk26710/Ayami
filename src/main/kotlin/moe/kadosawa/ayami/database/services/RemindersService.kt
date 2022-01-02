package moe.kadosawa.ayami.database.services

import moe.kadosawa.ayami.database.tables.NewReminder
import moe.kadosawa.ayami.database.tables.Reminder
import moe.kadosawa.ayami.database.tables.Reminders
import mu.KotlinLogging
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object RemindersService {
    private val logger = KotlinLogging.logger {}

    private fun error(msg: String): Nothing {
        throw IllegalStateException(msg)
    }

    /**
     * Converts queried reminder row into [Reminder]
     */
    private fun toReminder(row: ResultRow): Reminder =
        Reminder(
            id = row[Reminders.id],
            createdAt = row[Reminders.createdAt],
            triggerAt = row[Reminders.triggerAt],
            authorId = row[Reminders.authorId],
            guildId = row[Reminders.guildId],
            channelId = row[Reminders.channelId],
            messageId = row[Reminders.messageId],
            content = row[Reminders.content]
        )

    /**
     * Helper function to create new a [Reminder]
     */
    suspend fun add(newReminder: NewReminder): Reminder {
        val row = newSuspendedTransaction {
            val insert = Reminders.insert {
                it[triggerAt] = newReminder.triggerAt
                it[authorId] = newReminder.authorId
                it[guildId] = newReminder.guildId
                it[channelId] = newReminder.channelId
                it[content] = newReminder.content
            }

            insert.resultedValues?.firstOrNull() ?: error("nothing generated")
        }

        return toReminder(row)
    }
}