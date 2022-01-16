package moe.kadosawa.ayami

sealed class AyamiBaseException(message: String? = null, cause: Throwable? = null) : Throwable(message, cause)
class AyamiException(message: String? = null, cause: Throwable? = null) : AyamiBaseException(message, cause)
class CommandException(message: String? = null, cause: Throwable? = null) : AyamiBaseException(message, cause)
class CheckFailure(message: String? = null, cause: Throwable? = null) : AyamiBaseException(message, cause)
class BadArgument(message: String? = null, cause: Throwable? = null) : AyamiBaseException(message, cause)
