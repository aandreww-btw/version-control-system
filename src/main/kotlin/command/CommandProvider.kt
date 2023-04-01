package svcs.command

import command.*

class CommandProvider {

    companion object {
        fun getCommandByParam(command: String?): VCSCommand {
            return when (command) {
                AddCommand.name -> AddCommand()
                ConfigCommand.name -> ConfigCommand()
                CommitCommand.name -> CommitCommand()
                CheckoutCommand.name -> CheckoutCommand()
                LogCommand.name -> LogCommand()
                HelpCommand.name, null -> HelpCommand()
                else -> NotCommand(command)
            }
        }
    }
}
