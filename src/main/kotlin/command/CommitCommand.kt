package command

import svcs.file.FileWorker
import java.io.File
import java.security.MessageDigest

const val HASH_ALGORITHM = "SHA-256"
val COMMIT_REGEX = "commit \\w{64}".toRegex()

class CommitCommand : VCSCommand {

    private val fileWorker = FileWorker()

    companion object {
        const val name = "commit"
    }

    override fun doAction(commitMessage: String?) {
        if (commitMessage.isNullOrEmpty()) {
            println("Message was not passed.")
            return
        }
        val lastHash = getLastCommitHashIfExist()
        if (lastHash.isNullOrEmpty() || isFilesHasChanged(lastHash)) {
            commitChanges(commitMessage)
            return
        }
        println("Nothing to commit.")
    }

    private fun getLastCommitHashIfExist(): String? {
        return try {
            val commitLine = fileWorker.log.readLines().last { COMMIT_REGEX.matches(it) }
            commitLine.replace("commit ", "")
        } catch (error: NoSuchElementException) {
            null
        }
    }

    private fun isFilesHasChanged(lastHash: String): Boolean {
        val lastCommitDir = fileWorker.commitsDir.resolve(lastHash)
        return !fileWorker.index.readLines().all {
            val savedFile = lastCommitDir.resolve(it)
            if (!savedFile.exists()) return@all false
            val actualFile = File(it)
            return@all compareFilesContent(savedFile.readText(), actualFile.readText())
        }
    }

    private fun commitChanges(commitMessage: String) {
        val commitHash = getHash(commitMessage)
        val commitDir = fileWorker.commitsDir.resolve(commitHash)
        copyTrackedFiles(commitDir)
        logChanges(commitHash, commitMessage)
    }

    private fun copyTrackedFiles(commitDir: File) {
        commitDir.mkdir()
        fileWorker.index.readLines().forEach {
            val savedFile = commitDir.resolve(it)
            File(it).copyTo(savedFile)
        }
    }

    private fun logChanges(commitHash: String, commitMessage: String) {
        val logText  = fileWorker.log.readText()
        val userName = fileWorker.config.readText()
        fileWorker.log.writeText("commit $commitHash\nAuthor: $userName\n$commitMessage\n\n$logText")
        println("Changes are committed.")
    }

    private fun compareFilesContent(first: String, second: String): Boolean {
        return getHash(first) == getHash(second)
    }

    private fun getHash(message: String): String {
        val byteMessage = message.toByteArray()
        val digestFun = MessageDigest.getInstance(HASH_ALGORITHM)
        val hash = digestFun.digest(byteMessage)
        return hash.fold("") { str, it -> str + "%02x".format(it) }
    }
}
