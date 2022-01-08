package moe.kadosawa.ayami.genshin

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.plus

object GenshinUtils {
    /**
     * Finds the point in time when the specified amount of resin will be recovered
     */
    fun resinRefill(needed: Long, current: Long): Instant {
        if (needed < current) {
            throw IllegalArgumentException("The number needed must be greater than the current number!")
        }

        val deltaMinutes = (needed - current) * 8
        val now = Clock.System.now()
        return now.plus(deltaMinutes, DateTimeUnit.MINUTE)
    }
}