/*
 * Copyright 2022 kadosawa (kadosawa.moe)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package moe.kadosawa.ayami.jda.ui

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moe.kadosawa.ayami.Ayami
import mu.KotlinLogging
import net.dv8tion.jda.api.entities.Emoji
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.interactions.components.Button
import net.dv8tion.jda.api.interactions.components.ButtonStyle
import net.dv8tion.jda.internal.interactions.ButtonImpl
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

private val logger = KotlinLogging.logger { }

typealias ButtonCallback = suspend (event: ButtonClickEvent) -> Unit

// I am most likely doing some weird shit with waiter parameter here but well at least it works

/**
 * Creates a [Button] with callback
 *
 * @param label Label which will be used on a button
 * @param style Represents button's style, default is [ButtonStyle.SECONDARY]
 * @param disabled Indicates if the button should be disabled
 * @param emoji Represent an emoji which is used on the button
 * @param timeout When button's callback should be removed
 * @param waiter [CompletableDeferred] which is used to determine when to start scheduling deletion of the button's callback from the global [Ayami.buttons] map
 * @param callback Suspended function which is called when users click on the button
 *
 * @return Returns [Button] instance
 */
fun button(
    label: String?,
    style: ButtonStyle? = ButtonStyle.SECONDARY,
    disabled: Boolean = false,
    emoji: Emoji? = null,
    timeout: Duration = 5.minutes,
    waiter: CompletableDeferred<Unit> = CompletableDeferred(Unit),
    callback: ButtonCallback = {}
): Button {
    val btn = ButtonImpl(UUID.randomUUID().toString(), label, style, disabled, emoji)

    Ayami.buttons[btn.id!!] = callback
    Ayami.cleanupScope.launch {
        waiter.await()
        logger.debug { "Callback for button ${btn.id} will be expired in ${timeout}..." }
        delay(timeout)
        Ayami.buttons.remove(btn.id)
        logger.debug { "Callback for button ${btn.id} has been removed" }
    }

    return btn
}
