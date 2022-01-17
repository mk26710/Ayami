package moe.kadosawa.ayami.genshin

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import moe.kadosawa.ayami.AyamiException
import kotlin.time.Duration.Companion.minutes

object GenshinUtils {
    fun predictResin(current: Long, needed: Long): Instant {
        if (needed <= current) {
            throw AyamiException("needed resin must be greater than current!")
        }

        val now = Clock.System.now()
        val delta = (needed - current) * 8

        return now.plus(delta.minutes)
    }
}