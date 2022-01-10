package moe.kadosawa.ayami.discord.commands

import moe.kadosawa.ayami.discord.enums.MaterialType
import moe.kadosawa.ayami.extensions.await
import moe.kadosawa.ayami.extensions.isPrivate
import moe.kadosawa.ayami.genshin.GenshinService
import moe.kadosawa.ayami.genshin.models.GenshinCharacter
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.apache.commons.text.similarity.FuzzyScore
import java.util.*

object CharacterMaterials : BaseSlash() {
    override val path = "character/materials"

    override suspend fun invoke(event: SlashCommandEvent) {
        event.deferReply(event.isPrivate).await()
        val query = event.getOption("query")!!.asString
        val type = MaterialType.valueOf(event.getOption("type")!!.asString.uppercase())

        val results = mutableListOf<Pair<Int, GenshinCharacter>>()
        val score = FuzzyScore(Locale.ENGLISH)

        GenshinService.characters.forEach { c->
            val scored = score.fuzzyScore(c.enum.fullName, query) ?: 0
            if (scored > 0) {
                results.add(Pair(scored, c))
            }
        }

        val biggestScore = results.maxOfOrNull { it.first }
        val closestResults = results.filter { it.first == biggestScore }

        if (closestResults.isEmpty()) {
            event.hook.sendMessage("I tried really hard, but there's nothing similar in my database :(").await()
            return
        }

        if (closestResults.size > 1) {
            event.hook.sendMessage("There are multiple results:\n${closestResults.joinToString("\n")}").await()
        } else {
            val (_, character) = closestResults.first()
            val msg = when (type) {
                MaterialType.ASCENSION -> character.ascension
                MaterialType.TALENTS -> character.talents
            }

            event.hook.sendMessage("$msg").await()

        }
    }
}