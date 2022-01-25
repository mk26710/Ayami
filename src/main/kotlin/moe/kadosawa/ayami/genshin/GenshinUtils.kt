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