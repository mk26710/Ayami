package moe.kadosawa.ayami.commands

import kotlinx.coroutines.delay
import moe.kadosawa.ayami.extensions.await
import moe.kadosawa.ayami.interfaces.Command
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import dev.minn.jda.ktx.interactions.Command as commandData


class PingCommand : Command {
    override val data = commandData("ping", "Sends pong and then ping-pong")

    override suspend fun execute(event: SlashCommandEvent) {
        event.reply("Pong!").setEphemeral(true).await()
        delay(3000)
        event.hook.editOriginal("Ping-Pong!").await()
    }
}