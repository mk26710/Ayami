package moe.kadosawa.ayami

open class AyamiException(message: String? = null, cause: Throwable? = null) : Throwable(message, cause)
class CommandException(message: String? = null, cause: Throwable? = null) : AyamiException(message, cause)
class CheckFailure(message: String? = null, cause: Throwable? = null) : AyamiException(message, cause)
class BadArgument(message: String? = null, cause: Throwable? = null) : AyamiException(message, cause)
