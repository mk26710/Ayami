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

package moe.kadosawa.ayami.utils

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default

private val argParser = ArgParser("Ayami")

val configPath by argParser.option(ArgType.String, fullName = "config", shortName = "c")
    .default("config.properties")

val refreshSlash by argParser.option(ArgType.Boolean, fullName = "refreshSlash")
    .default(false)

val dbInit by argParser.option(ArgType.Boolean, fullName = "dbInit")
    .default(false)

fun parseArgs(args: Array<String>) = argParser.parse(args)