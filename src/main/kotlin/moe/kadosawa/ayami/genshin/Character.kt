package moe.kadosawa.ayami.genshin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    @SerialName("ENUM")
    val enum: Characters,
    @SerialName("ASCENSION")
    val ascension: String?,
    @SerialName("TALENTS")
    val talents: String?,
    @SerialName("GUIDE")
    val guide: String?
)