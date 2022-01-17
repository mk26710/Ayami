package moe.kadosawa.ayami.commands

import moe.kadosawa.ayami.jda.await
import moe.kadosawa.ayami.jda.isPrivate
import moe.kadosawa.ayami.jda.tryDefer
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import kotlin.system.measureTimeMillis

class PingCommand : SlashCommand() {
    override val path = "ping"

    override suspend fun invoke(e: SlashCommandEvent) {
        e.tryDefer(e.isPrivate)?.await()

        val latency = measureTimeMillis {
            e.hook.sendMessage("Ping...").await()
        }

        e.hook.editOriginal("Pong! (`${latency}ms`)").await()
    }
}