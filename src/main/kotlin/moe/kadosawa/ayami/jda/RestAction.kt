package moe.kadosawa.ayami.jda

import kotlinx.coroutines.future.asDeferred
import net.dv8tion.jda.api.requests.RestAction

/**
 * Waits for a result of [RestAction] without blocking the thread
 */
suspend fun <T> RestAction<T>.await(): T {
    return submit().asDeferred().await()
}