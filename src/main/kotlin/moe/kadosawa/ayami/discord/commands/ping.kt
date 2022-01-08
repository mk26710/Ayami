package moe.kadosawa.ayami.discord.commands

import moe.kadosawa.ayami.discord.extensions.await
import moe.kadosawa.ayami.discord.extensions.isPrivate
import moe.kadosawa.ayami.discord.interfaces.SlashExecutor
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import kotlin.system.measureTimeMillis

object PingSlash : SlashExecutor() {
    override val path = "ping"

    override suspend fun invoke(event: SlashCommandEvent) {
        event.deferReply(event.isPrivate).await()
        val rtt = measureTimeMillis {
            event.hook.sendMessage("Pong...").await()
        }

        event.hook.editOriginal("Pong! (Took `$rtt`ms)").await()
    }
}