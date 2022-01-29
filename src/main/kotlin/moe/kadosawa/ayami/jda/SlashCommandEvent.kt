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

package moe.kadosawa.ayami.jda

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction

/**
 * Tells if user wants an ephemeral response
 */
val SlashCommandInteractionEvent.isPrivate
    get() = getOption("private")?.asBoolean ?: false

/**
 * Acknowledge interaction if it wasn't acknowledged
 */
fun SlashCommandInteractionEvent.tryDefer(ephemeral: Boolean = false): ReplyCallbackAction? {
    if (isAcknowledged) {
        return null
    }

    return deferReply(ephemeral)
}