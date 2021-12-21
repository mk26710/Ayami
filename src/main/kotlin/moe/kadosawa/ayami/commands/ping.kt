package moe.kadosawa.ayami.commands

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent

fun handlePing(event: SlashCommandEvent) {
    event.reply("Pong!").queue()
}