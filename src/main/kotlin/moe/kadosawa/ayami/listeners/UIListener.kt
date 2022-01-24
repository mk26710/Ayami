package moe.kadosawa.ayami.listeners

import kotlinx.coroutines.launch
import moe.kadosawa.ayami.Ayami
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class UIListener : ListenerAdapter() {
    override fun onButtonClick(event: ButtonClickEvent) {
        val id = event.button?.id
            ?: return

        val callback = Ayami.buttons[id]
            ?: return

        Ayami.defaultScope.launch {
            callback.invoke(event)
        }
    }
}