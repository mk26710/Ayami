package moe.kadosawa.ayami.utils

import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData

val privateOptionData =
    OptionData(OptionType.BOOLEAN, "private", "Choose whether you want response to be seen by everyone or not")
