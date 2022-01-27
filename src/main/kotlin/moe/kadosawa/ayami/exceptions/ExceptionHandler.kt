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

package moe.kadosawa.ayami.exceptions

import moe.kadosawa.ayami.jda.await
import moe.kadosawa.ayami.jda.tryDefer
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent

object ExceptionHandler {
    suspend fun handle(event: SlashCommandEvent, ex: AyamiBaseException) {
        event.tryDefer(true)?.await()

        when (ex) {
            is CheckFailure -> {
                event.hook.sendMessage(ex.message ?: "Missing access.").await()
            }

            is BadArgument -> {
                event.hook.sendMessage(ex.message ?: "Invalid command argument.").await()
            }

            is CommandException, is AyamiException -> {
                event.hook.sendMessage(ex.message ?: "Unknown error.").await()
            }
        }
    }
}

