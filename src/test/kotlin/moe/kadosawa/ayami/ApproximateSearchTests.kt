package moe.kadosawa.ayami

import moe.kadosawa.ayami.genshin.Characters
import kotlin.test.Test
import kotlin.test.assertEquals

class ApproximateSearchTests {
    private fun runQueries(target: Characters, queries: List<String>) {
        queries.forEach { query ->
            val enum = Characters.fromSimilarName(query)
            assertEquals(target, enum, "Querying by $query should've resulted with a different enum!")
        }
    }

    @Test
    fun testKamisatoAyakaLookups() {
        val target = Characters.KAMISATO_AYAKA
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
        val target = Characters.RAIDEN_SHOGUN
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
