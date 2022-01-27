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

package moe.kadosawa.ayami.jda

import kotlinx.datetime.Instant
import net.dv8tion.jda.api.utils.TimeFormat

fun timeShort(instant: Instant) =
    TimeFormat.TIME_SHORT.format(instant.toEpochMilliseconds())

fun timeLong(instant: Instant) =
    TimeFormat.TIME_LONG.format(instant.toEpochMilliseconds())

fun dateShort(instant: Instant) =
    TimeFormat.DATE_SHORT.format(instant.toEpochMilliseconds())

fun dateLong(instant: Instant) =
    TimeFormat.DATE_LONG.format(instant.toEpochMilliseconds())

fun dateTimeShort(instant: Instant) =
    TimeFormat.DATE_TIME_SHORT.format(instant.toEpochMilliseconds())

fun dateTimeLong(instant: Instant) =
    TimeFormat.DATE_TIME_LONG.format(instant.toEpochMilliseconds())

fun relative(instant: Instant) =
    TimeFormat.RELATIVE.format(instant.toEpochMilliseconds())
