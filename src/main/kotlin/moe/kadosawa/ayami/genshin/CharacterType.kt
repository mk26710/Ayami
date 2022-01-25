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

/*
 * I know that this file is the ugliest of all, but
 * my brain is too smooth for something smarter :(
 */

package moe.kadosawa.ayami.genshin

import com.google.common.collect.Collections2
import moe.kadosawa.ayami.AyamiException
import org.apache.commons.text.similarity.JaroWinklerSimilarity
import kotlin.reflect.full.findAnnotation

private fun String.permute() =
    Collections2.permutations(split(Regex("\\s+"))).map { it.joinToString(" ") }

private fun String.normal() = trim()
    .replace("_", " ")
    .split("\\s+".toRegex())
    .joinToString(" ")
    .lowercase()

/**
 * Prevents character name and aliases from permutation
 */
private annotation class DisablePermutation(val value: Array<String>)

/**
 * Enumeration of playable Genshin Impact
 * characters as of version 2.4
 */
@DisablePermutation(["HU_TAO", "YUN_JIN"])
enum class CharacterType {
    ALBEDO,
    ALOY,
    ARATAKI_ITTO,
    BARBARA,
    BEIDOU,
    BENNETT,
    CHONGYUN,
    DILUC,
    DIONA,
    EULA,
    FISCHL,
    GANYU,
    GOROU,
    HU_TAO,
    JEAN,
    KAEDEHARA_KAZUHA,
    KAEYA,
    KAMISATO_AYAKA,
    KEQING,
    KLEE,
    KUJOU_SARA,
    LISA,
    MONA,
    NINGGUANG,
    NOELLE,
    QIQI,
    RAIDEN_SHOGUN,
    RAZOR,
    ROSARIA,
    SANGONOMIYA_KOKOMI,
    SAYU,
    SHENHE,
    SUCROSE,
    TARTAGLIA,
    THOMA,
    VENTI,
    XIANGLING,
    XIAO,
    XINGQIU,
    XINYAN,
    YANFEI,
    YOIMIYA,
    YUN_JIN,
    ZHONGLI;
}

/**
 * Map of name aliases for characters that have them
 */
private val aliases = mapOf(
    CharacterType.JEAN to listOf("Jean Gunnhildr"),
    CharacterType.YUN_JIN to listOf("Yunjin"),
    CharacterType.RAIDEN_SHOGUN to listOf("Ei", "Baal"),
)

/**
 * Map of names of all characters
 */
private val names = CharacterType.values().associateWith { it.name }

/**
 * Map of characters names and aliases
 * which are also normalized and permuted
 */
private val permutations = CharacterType.values().associateWith { char ->
    val name = names[char]?.normal()
        ?: throw AyamiException("Could not find a name for $char in map of names")

    val aliases = aliases.getOrDefault(char, listOf()).map { it.normal() }

    val merged = aliases + name

    val permutationDisabled = CharacterType::class.findAnnotation<DisablePermutation>()?.value
    if (permutationDisabled?.contains(char.name) == true) {
        return@associateWith merged
    }

    merged.flatMap { it.permute() }
}

/**
 * Returns a [CharacterType] using Jaro Winkler similarity
 */
fun characterTypeFromSimilarName(query: String): CharacterType? {
    val similarity = JaroWinklerSimilarity()
    val normalQuery = query.normal()

    val results = mutableListOf<Pair<CharacterType, Double>>()

    CharacterType.values().forEach { char ->
        permutations.getOrDefault(char, listOf()).forEach { name ->
            val score = similarity.apply(normalQuery, name)
            results.add(Pair(char, score))
        }
    }

    return results.maxWithOrNull(compareBy { it.second })?.first
}