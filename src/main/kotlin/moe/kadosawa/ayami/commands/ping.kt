package moe.kadosawa.ayami.commands

import dev.minn.jda.ktx.interactions.option
import kotlinx.coroutines.delay
import moe.kadosawa.ayami.extensions.await
import moe.kadosawa.ayami.interfaces.Slash
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import dev.minn.jda.ktx.interactions.Command as commandData


class PingSlash : Slash() {
    override val data = commandData("ping", "Sends pong and then ping-pong") {
        option<Boolean>("private", "Choose whether you want response to be seen by everyone or not")
    }

    override suspend fun execute(event: SlashCommandEvent) {
        event.reply("Pong!").setEphemeral(isPrivate(event)).await()
        delay(3000)
        event.hook.editOriginal("Ping-Pong!").await()
    }
}