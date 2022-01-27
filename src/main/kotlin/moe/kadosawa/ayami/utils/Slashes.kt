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

import moe.kadosawa.ayami.jda.command
import moe.kadosawa.ayami.jda.option
import moe.kadosawa.ayami.jda.privacyOption
import moe.kadosawa.ayami.jda.subcommandData
import net.dv8tion.jda.api.interactions.commands.OptionType

object Slashes {
    val debugData = listOf(
        command("refresh-commands", "Sends command update request to API") {
            option(OptionType.BOOLEAN, "global", "Global commands list")
            option(OptionType.BOOLEAN, "debug", "Debug guild commands list")
        }
    )

    val globalData = listOf(
        command("ping", "Sends ping and then ping pong") {
            privacyOption()
        },

        command("character", "Get data about genshin impact characters") {
            subcommandData("materials", "Check what ascension and talent materials are") {
                option(OptionType.STRING, "type", "What sort fo materials you are looking for") {
                    addChoice("ascension", "ASCENSION")
                    addChoice("talents", "TALENTS")
                }
                option(OptionType.STRING, "query", "Character name (e.g. Kamisato Ayaka, Yae Miko, etc.)")
                privacyOption()
            }
        },

        command("resin", "Calculate when you'll have specified amount of resin") {
            option(OptionType.INTEGER, "current", "Your current amount of resin") {
                setRequiredRange(0, 159)
            }
            option(OptionType.INTEGER, "needed", "Amount of resin you need") {
                setRequiredRange(1, 160)
            }
            privacyOption()
        },

        command("reminder", "Manage reminders") {
            subcommandData("add", "Create a new reminder") {
                option(OptionType.STRING, "duration", "ISO-8601 duration format")
                option(OptionType.STRING, "content", "Message that you will receive")
                privacyOption()
            }

            subcommandData("list", "Shows list of your active reminders")

            subcommandData("remove", "Remove a reminder") {
                option(OptionType.INTEGER, "id", "Reminder ID", required = false)
                option(OptionType.BOOLEAN, "all", "Delete all reminders", required = false)
            }
        }
    )

    val combinedData = debugData + globalData
}