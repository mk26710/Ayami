package moe.kadosawa.ayami.commands

import moe.kadosawa.ayami.extensions.await
import moe.kadosawa.ayami.extensions.isPrivate
import moe.kadosawa.ayami.interfaces.SlashExecutor
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import kotlin.system.measureTimeMillis

class PingSlash : SlashExecutor() {
    override val path = "ping"

    override suspend fun execute(event: SlashCommandEvent) {
        event.deferReply(event.isPrivate).await()
        val rtt = measureTimeMillis {
            event.hook.sendMessage("Pong...").await()
        }

        event.hook.editOriginal("Pong! (Took `$rtt`ms)").await()
    }
}