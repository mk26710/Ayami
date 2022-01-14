package moe.kadosawa.ayami.genshin

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import moe.kadosawa.ayami.genshin.models.Character
import java.net.URL

object GenshinService {
    private val resource: URL? = this::class.java.getResource("/characters.json")

    private val json = Json {
        prettyPrint = true
        encodeDefaults = true
    }

    /**
     * List of characters from resources/characters.json
     */
    val characters = json.decodeFromString<List<Character>>(resource!!.readText())
}