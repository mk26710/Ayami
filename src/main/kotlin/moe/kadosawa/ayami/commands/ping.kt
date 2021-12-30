package moe.kadosawa.ayami.commands

import kotlinx.coroutines.delay
import moe.kadosawa.ayami.extensions.await
import moe.kadosawa.ayami.extensions.isPrivate
import moe.kadosawa.ayami.interfaces.SlashExecutor
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent

class PingSlash : SlashExecutor() {
    override val path = "ping"

    override suspend fun execute(event: SlashCommandEvent) {
        event.reply("Pong!").setEphemeral(event.isPrivate).await()
        delay(3000)
        event.hook.editOriginal("Ping-Pong!").await()
    }
}