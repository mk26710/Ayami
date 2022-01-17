package moe.kadosawa.ayami.genshin

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object Genshin {
    private val json = Json {
        prettyPrint = true
        encodeDefaults = true
    }

    val characters: List<Character> =
        json.decodeFromString(this::class.java.getResource("/characters.json")!!.readText())

}