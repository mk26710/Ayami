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

package moe.kadosawa.ayami.database.tables

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object Reminders : Table() {
    val id = long("id").autoIncrement().uniqueIndex()

    val createdAt = timestamp("createdAt").index().defaultExpression(CurrentTimestamp())
    val triggerAt = timestamp("triggerAt").index()
    val authorId = long("authorId")
    val guildId = long("guildId").nullable().default(null)
    val channelId = long("channelId").nullable().default(null)
    val messageId = long("messageId").index().nullable().default(null)
    val content = text("content").default("...")

    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Reminder(
    val id: Long,
    val createdAt: Instant,
    val triggerAt: Instant,
    val authorId: Long,
    val guildId: Long?,
    val channelId: Long?,
    val messageId: Long?,
    val content: String
)

@Serializable
data class NewReminder(
    val triggerAt: Instant,
    val authorId: Long,
    val guildId: Long? = null,
    val channelId: Long? = null,
    val messageId: Long? = null,
    val content: String = "...",
)