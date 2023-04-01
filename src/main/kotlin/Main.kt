package svcs

import svcs.command.CommandProvider

fun main(args: Array<String>) {
    val (parameter, argument) = Array(2) { if (args.size > (it)) args[it] else null}
    val command = CommandProvider.getCommandByParam(parameter)
    command.proceedAction(argument)
}
