package moe.kadosawa.ayami.discord.commands

import moe.kadosawa.ayami.discord.enums.MaterialType
import moe.kadosawa.ayami.extensions.await
import moe.kadosawa.ayami.extensions.isPrivate
import moe.kadosawa.ayami.genshin.GenshinService
import moe.kadosawa.ayami.genshin.enums.GenshinCharacters
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent

object CharacterMaterials : BaseSlash() {
    override val path = "character/materials"

    override suspend fun invoke(event: SlashCommandEvent) {
        event.deferReply(event.isPrivate).await()
        val query = event.getOption("query")!!.asString
        val type = MaterialType.valueOf(event.getOption("type")!!.asString.uppercase())

        val bestResultEnum = GenshinCharacters.approximateByFullName(query)

        if (bestResultEnum == null) {
            event.hook.sendMessage("I tried really hard but nothing was found by that query :(")
            return
        }

        val bestResult = GenshinService.characters.find { it.enum == bestResultEnum }

        val msg = when(type) {
            MaterialType.ASCENSION -> bestResult?.ascension
            MaterialType.TALENTS -> bestResult?.talents
        }

        event.hook.sendMessage("$msg").await()


    }
}