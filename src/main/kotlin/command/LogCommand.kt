package command

import svcs.file.FileWorker

class LogCommand : VCSCommand {

    private val fileWorker = FileWorker()

    companion object {
        const val name = "log"
    }

    override fun doAction(argument: String?) {
        if (fileWorker.log.length() > 0) {
            fileWorker.log.forEachLine { println(it) }
        } else {
            println("No commits yet.")
        }
    }
}
