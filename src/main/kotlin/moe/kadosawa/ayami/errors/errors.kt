package moe.kadosawa.ayami.errors

/**
 * Base error for commands
 */
sealed class CommandError(message: String? = null, cause: Throwable? = null) : Throwable(message, cause)

class CommandInvokeError(message: String? = null, cause: Throwable) : CommandError(message, cause)
class BadArgument(message: String) : CommandError(message)

