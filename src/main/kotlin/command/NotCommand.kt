package command

class NotCommand(command: String) : VCSCommand {
    private val commandName: String = command

    override fun doAction(argument: String?) {
        println("'$commandName' is not a SVCS command.")
    }
}