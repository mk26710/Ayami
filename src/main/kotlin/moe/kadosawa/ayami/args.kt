package moe.kadosawa.ayami

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