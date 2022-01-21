package moe.kadosawa.ayami.commands

import moe.kadosawa.ayami.abc.SlashCommand
import moe.kadosawa.ayami.enums.MaterialType
import moe.kadosawa.ayami.genshin.Character
import moe.kadosawa.ayami.genshin.fromSimilarName
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

        val character = Character.fromSimilarName(query)

        val message = when (type) {
            MaterialType.ASCENSION -> character.ascension
            MaterialType.TALENTS -> character.talents
        }

        e.hook.sendMessage("$message").await()
    }
}