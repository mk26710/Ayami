/*
 * I know that this file is the ugliest of all, but
 * my brain is too smooth for something smarter :(
 */

package moe.kadosawa.ayami.genshin

import com.google.common.collect.Collections2
import org.apache.commons.text.similarity.JaroWinklerSimilarity

private fun String.permute() =
    Collections2.permutations(split(Regex("\\s+"))).map { it.joinToString(" ") }

/**
 * Enumeration of playable Genshin Impact
 * characters as of version 2.4
 */
enum class Characters(val aliases: List<String>) {
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
    HU_TAO {
        override val permuteNames = false
    },
    JEAN("Jean Gunnhildr".permute()),
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
    RAIDEN_SHOGUN("Ei", "Baal"),
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

    constructor(vararg aliases: String) : this(aliases.toList())

    open val permuteNames: Boolean = true

    companion object {
        private fun String.normal() = trim()
            .replace("_", " ")
            .split("\\s+".toRegex())
            .joinToString(" ")
            .lowercase()

        fun fromSimilarName(query: String): Characters? {
            val similarity = JaroWinklerSimilarity()
            val normalQuery = query.normal()

            val results = mutableListOf<Pair<Characters, Double>>()

            values().forEach { char ->
                val aliases = char.aliases.map { it.normal() }
                val names = if (char.permuteNames) {
                    char.name.normal().permute()
                } else {
                    listOf(char.name.normal())
                }

                (names + aliases).forEach { name ->
                    val score = similarity.apply(normalQuery, name)
                    results.add(Pair(char, score))
                }
            }

            return results.maxWithOrNull(compareBy { it.second })?.first
        }
    }
}