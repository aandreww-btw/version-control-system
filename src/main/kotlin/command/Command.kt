package command

import svcs.file.FileWorker

interface VCSCommand {

    fun doAction(argument: String?)

    fun proceedAction(argument: String?) {
        FileWorker().createVCSStructureIfNotExist()
        doAction(argument)
    }
}

