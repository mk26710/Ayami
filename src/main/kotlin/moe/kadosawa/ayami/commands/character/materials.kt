package moe.kadosawa.ayami.commands.character

import moe.kadosawa.ayami.extensions.await
import moe.kadosawa.ayami.extensions.isPrivate
import moe.kadosawa.ayami.interfaces.SlashExecutor
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.apache.commons.text.similarity.FuzzyScore
import java.util.*

object CharacterMaterialsSlash : SlashExecutor() {
    override val path = "character/materials"

    val characters = listOf(
        "Albedo",
        "Aloy",
        "Amber",
        "Arataki Itto",
        "Barbara",
        "Beidou",
        "Bennett",
        "Chongyun",
        "Diluc",
        "Diona",
        "Eula",
        "Fischl",
        "Ganyu",
        "Gorou",
        "Hu Tao",
        "Jean",
        "Kaeya",
        "Kazuha",
        "Kamisato Ayaka",
        "Keqing",
        "Klee",
        "Kojou Sara",
        "Lisa",
        "Mona",
        "Ningguang",
        "Noelle",
        "Qiqi",
        "Raiden Shogun",
        "Razor",
        "Sangonomiya Kokomi",
        "Sayu",
        "Sucrose",
        "Tartaglia",
        "Thoma",
        "Venti",
        "Xiangling",
        "Xiao",
        "Xingqiu",
        "Xinyan",
        "Yanfei",
        "Yoimiya",
        "Zhongli",
    )


    override suspend fun invoke(event: SlashCommandEvent) {
        event.deferReply(event.isPrivate).await()
        val query = event.getOption("query")!!.asString

        val results = mutableListOf<Pair<Int, String>>()

        val score = FuzzyScore(Locale.ENGLISH)
        characters.forEach { name ->
            val scored = score.fuzzyScore(name, query) ?: 0
            if (scored > 0) {
                results.add(Pair(scored, name))
            }
        }

        if (results.isEmpty()) {
            // TODO: Could make a better error message here
            event.hook.sendMessage("I tried really hard, but there's nothing similar in my database :(").await()
            return
        }

        // sort just for testing
        results.sortByDescending { el -> el.first }
        val readableResults = results.take(10).joinToString(separator = "\n")
        event.hook.sendMessage("Hello! Your query is: $query\n\nResults: \n$readableResults").await()
    }
}