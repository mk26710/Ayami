package moe.kadosawa.ayami.interfaces

import moe.kadosawa.ayami.errors.CheckFailure
import moe.kadosawa.ayami.utils.Config
import mu.KotlinLogging
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent

abstract class SlashExecutor {
    @Suppress("unused")
    private val logger = KotlinLogging.logger {}

    /**
     * Command path
     */
    abstract val path: String

    /**
     * If set to true, command will not be added to the global
     * slashes commands list of the bot. Default is false.
     */
    open val debugOnly: Boolean = false

    /**
     * Check that runs before execution and responds with an
     * error message to a user if return value is false
     */
    open suspend fun check(event: SlashCommandEvent): Boolean = true

    /**
     * Code that gets executed if checks don't fail;
     * Return value represents execution exit status
     */
    abstract suspend fun invoke(event: SlashCommandEvent)

    /**
     * Runner function performing all checks
     * and then proceeding to the execution
     */
    suspend fun run(event: SlashCommandEvent) {
        if (debugOnly) {
            if (event.guild?.id != Config.Bot.guild) {
                throw CheckFailure("This is debug only command, you can't run it here.")
            }
        }

        val hasAccess = check(event)

        if (!hasAccess) {
            throw CheckFailure("Sorry, you don't have access to this command.")
        }

        invoke(event)
    }
}