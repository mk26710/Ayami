package moe.kadosawa.ayami.errors

/**
 * Base error for every custom error in this project
 */
open class AyamiError(message: String? = null, cause: Throwable? = null) : Throwable(message, cause)