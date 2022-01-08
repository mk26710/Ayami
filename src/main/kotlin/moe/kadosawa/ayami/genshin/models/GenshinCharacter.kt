package moe.kadosawa.ayami.genshin.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import moe.kadosawa.ayami.genshin.GenshinCharacters

@Serializable
data class GenshinCharacter(
    @SerialName("ENUM")
    val enum: GenshinCharacters,
    @SerialName("ASCENSION")
    val ascension: String?,
    @SerialName("TALENTS")
    val talents: String?,
    @SerialName("GUIDE")
    val guide: String?
)