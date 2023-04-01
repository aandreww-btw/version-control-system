package command

import svcs.file.FileWorker
import java.io.File

class CheckoutCommand : VCSCommand {

    private val fileWorker = FileWorker()

    companion object {
        const val name = "checkout"
    }

    override fun doAction(commitId: String?) {
        if (commitId.isNullOrEmpty()) {
            println("Commit id was not passed.")
            return
        }
        val exactCommitDir = fileWorker.commitsDir.resolve(commitId)
        if (!exactCommitDir.exists()) {
            println("Commit does not exist.")
            return
        }
        exactCommitDir.walkTopDown().forEach {
            if (it.isFile) {
                File(it.name).writeText(it.readText())
            }
        }
        println("Switched to commit $commitId.")
    }
}
