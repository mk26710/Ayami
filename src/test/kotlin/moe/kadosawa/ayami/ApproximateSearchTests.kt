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

package moe.kadosawa.ayami

import moe.kadosawa.ayami.genshin.CharacterType
import moe.kadosawa.ayami.genshin.characterTypeFromSimilarName
import kotlin.test.Test
import kotlin.test.assertEquals

class ApproximateSearchTests {
    private fun runQueries(target: CharacterType, queries: List<String>) {
        queries.forEach { query ->
            val enum = characterTypeFromSimilarName(query)
            assertEquals(target, enum, "Querying by $query should've resulted with a different enum!")
        }
    }

    @Test
    fun testKamisatoAyakaLookups() {
        val target = CharacterType.KAMISATO_AYAKA
        val queries = listOf(
            "Kamisato Ayaka",
            "Ayaka Kamisato",
            "Ayaka",
            "Ayak Kasmiato",
            "kamisato ayaya",
            "ayaka kamisat",
            "kmisato ayaks",
        )

        runQueries(target, queries)
    }

    @Test
    fun testRaidenShogunLookups() {
        val target = CharacterType.RAIDEN_SHOGUN
        val queries = listOf(
            "Raiden Shogun",
            "Shogun Raiden",
            "Raiden",
            "Shogun",
            "Baal",
            "Ei",
        )

        runQueries(target, queries)
    }
}
