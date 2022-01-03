package moe.kadosawa.ayami.commands

import moe.kadosawa.ayami.core.Ayami
import moe.kadosawa.ayami.errors.BadArgument
import moe.kadosawa.ayami.extensions.await
import moe.kadosawa.ayami.interfaces.SlashExecutor
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent

object RefreshCommandsSlash : SlashExecutor() {
    override val path = "refresh-commands"

    override val debugOnly = true

    override suspend fun check(event: SlashCommandEvent): Boolean {
        return event.user.id == Ayami.appInfo.owner.id
    }

    override suspend fun invoke(event: SlashCommandEvent) {
        event.deferReply(true).await()

        val global = event.getOption("global")!!.asBoolean
        val debug = event.getOption("debug")!!.asBoolean

        if (!global && !debug) {
            throw BadArgument("What do you expect? Both arguments are false, nothing was refreshed.")
        }

        Ayami.refreshCommands(global, debug)
        event.hook.sendMessage("Commands update request was sent").await()
    }
}