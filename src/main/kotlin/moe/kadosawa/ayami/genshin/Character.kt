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

package moe.kadosawa.ayami.genshin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import moe.kadosawa.ayami.exceptions.AyamiException

@Serializable
data class Character(
    @SerialName("ENUM")
    val enum: CharacterType,
    @SerialName("ASCENSION")
    val ascension: String?,
    @SerialName("TALENTS")
    val talents: String?,
    @SerialName("GUIDE")
    val guide: String?
)

fun Character.Companion.fromSimilarName(query: String): Character {
    val similarEnum = characterTypeFromSimilarName(query)
        ?: throw AyamiException("unable to find enum for character by $query")

    return Genshin.characters.find { it.enum == similarEnum }
        ?: throw AyamiException("unable to find character data for $similarEnum")
}