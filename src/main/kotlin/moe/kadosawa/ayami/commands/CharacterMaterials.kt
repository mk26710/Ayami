package moe.kadosawa.ayami.commands

import moe.kadosawa.ayami.enums.MaterialType
import moe.kadosawa.ayami.genshin.Characters
import moe.kadosawa.ayami.genshin.Genshin
import moe.kadosawa.ayami.jda.await
import moe.kadosawa.ayami.jda.isPrivate
import moe.kadosawa.ayami.jda.tryDefer
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent

class CharacterMaterials : SlashCommand() {
    override val path = "character/materials"

    override suspend fun invoke(e: SlashCommandEvent) {
        e.tryDefer(e.isPrivate)?.await()

        val query = e.getOption("query")!!.asString
        val type = e.getOption("type")!!.asString.let(MaterialType::valueOf)

        val approxCharacter = Characters.approximateByFullName(query)
        if (approxCharacter === null) {
            e.hook.sendMessage("Sorry, but I couldn't find anything similar to that query.").await()
            return
        }

        val character = Genshin.characters.find { it.enum == approxCharacter }
        val message = when (type) {
            MaterialType.ASCENSION -> character?.ascension
            MaterialType.TALENTS -> character?.talents
        }

        e.hook.sendMessage("$message").await()
    }
}