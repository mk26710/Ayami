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

package moe.kadosawa.ayami.commands

import moe.kadosawa.ayami.abc.SlashCommand
import moe.kadosawa.ayami.enums.MaterialType
import moe.kadosawa.ayami.genshin.Character
import moe.kadosawa.ayami.genshin.fromSimilarName
import moe.kadosawa.ayami.jda.await
import moe.kadosawa.ayami.jda.isPrivate
import moe.kadosawa.ayami.jda.tryDefer
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent

class CharacterMaterials : SlashCommand("character/materials") {
    override suspend fun invoke(e: SlashCommandEvent) {
        e.tryDefer(e.isPrivate)?.await()

        val query = e.getOption("query")!!.asString
        val type = e.getOption("type")!!.asString.let(MaterialType::valueOf)

        val character = Character.fromSimilarName(query)

        val message = when (type) {
            MaterialType.ASCENSION -> character.ascension
            MaterialType.TALENTS -> character.talents
        }

        e.hook.sendMessage("$message").await()
    }
}