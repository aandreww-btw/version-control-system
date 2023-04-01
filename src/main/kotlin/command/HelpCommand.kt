package command

class HelpCommand : VCSCommand {

    companion object {
        const val name = "--help"
    }

    override fun doAction(arg: String?) {
        println("These are SVCS commands:\nconfig     Get and set a username.\nadd        Add a file to the " +
                "index.\nlog        Show commit logs.\ncommit     Save changes.\ncheckout   Restore a file.")
    }
}