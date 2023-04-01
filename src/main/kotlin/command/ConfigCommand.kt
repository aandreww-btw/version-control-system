package command

import svcs.file.FileWorker

class ConfigCommand : VCSCommand {

    private val fileWorker = FileWorker()

    companion object {
        const val name = "config"
    }

    override fun doAction(userName: String?) {
        if (!userName.isNullOrEmpty()) {
            fileWorker.config.writeText(userName)
            println("The username is $userName.")
        } else {
            val storedUserName = fileWorker.config.readText()
            if (storedUserName.isNotEmpty()) {
                println("The username is $storedUserName.")
            } else {
                println("Please, tell me who you are.")
            }
        }
    }
}
