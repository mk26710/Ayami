package moe.kadosawa.ayami.extensions

import moe.kadosawa.ayami.commands
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent

fun JDA.addCommand(name: String, description: String, callback: (event: SlashCommandEvent) -> Any) {
    commands[name] = callback
    this.upsertCommand(name, description).queue()
}
