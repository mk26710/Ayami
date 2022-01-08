package moe.kadosawa.ayami.discord.core

import moe.kadosawa.ayami.discord.commands.PingSlash
import moe.kadosawa.ayami.discord.commands.RefreshCommandsSlash
import moe.kadosawa.ayami.discord.commands.ResinSlash
import moe.kadosawa.ayami.discord.commands.character.CharacterMaterialsSlash
import moe.kadosawa.ayami.discord.commands.reminder.ReminderAddSlash
import moe.kadosawa.ayami.discord.extensions.command
import moe.kadosawa.ayami.discord.extensions.option
import moe.kadosawa.ayami.discord.extensions.privacyOption
import moe.kadosawa.ayami.discord.extensions.subcommandData
import net.dv8tion.jda.api.interactions.commands.OptionType

object Slashes {
    val executors = listOf(
        PingSlash,
        ResinSlash,
        ReminderAddSlash,
        CharacterMaterialsSlash,
        RefreshCommandsSlash
    ).associateBy { it.path }

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
        }
    )

    val combinedData = debugData + globalData
}