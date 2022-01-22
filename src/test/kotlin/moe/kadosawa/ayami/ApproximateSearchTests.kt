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
