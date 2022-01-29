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

package moe.kadosawa.ayami.abc

import moe.kadosawa.ayami.exceptions.CheckFailure
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

abstract class SlashCommand(val path: String) {
    open suspend fun check(e: SlashCommandInteractionEvent): Boolean = true

    abstract suspend fun invoke(e: SlashCommandInteractionEvent)

    suspend fun run(e: SlashCommandInteractionEvent) {
        val canRun = check(e)
        if (!canRun) {
            throw CheckFailure("You don't have access to this command, sorry.")
        }

        invoke(e)
    }
}