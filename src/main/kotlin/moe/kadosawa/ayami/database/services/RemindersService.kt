/*
 * Copyright 2022 kadosawa (kadosawa.moe)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package moe.kadosawa.ayami.database.services

import moe.kadosawa.ayami.database.tables.NewReminder
import moe.kadosawa.ayami.database.tables.Reminder
import moe.kadosawa.ayami.database.tables.Reminders
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*
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

    /**
     * Returns a list of [Reminder]s that belong to specified
     * user in the specified guild (if it's not null)
     */
    suspend fun getUserReminders(userId: Long, guildId: Long?): List<Reminder> {
        val rows = newSuspendedTransaction {
            Reminders
                .select { (Reminders.authorId eq userId) and (Reminders.guildId eq guildId) }
                .orderBy(Reminders.triggerAt to SortOrder.ASC)
                .toList()
        }

        return rows.map { toReminder(it) }
    }
}