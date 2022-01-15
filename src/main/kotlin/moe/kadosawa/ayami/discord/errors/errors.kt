package moe.kadosawa.ayami.discord.errors

/**
 * Base error for commands
 */
sealed class CommandError(message: String? = null, cause: Throwable? = null) : Throwable(message, cause)

class CommandInvokeError(message: String? = null, cause: Throwable) : CommandError(message, cause)
class CheckFailure(message: String?) : CommandError(message)
class BadArgument(message: String?) : CommandError(message)
