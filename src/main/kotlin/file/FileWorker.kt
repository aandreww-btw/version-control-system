package svcs.file

import java.io.File

const val VCS_FILE_NAME = "vcs"
const val CONFIG_FILE_NAME = "config.txt"
const val INDEX_FILE_NAME = "index.txt"
const val LOG_FILE_NAME = "log.txt"
const val COMMITS_DIR_NAME = "commits"

class FileWorker {

    private val separator: String get() = File.separator

    private val vcsDirPath: String get() = ".$separator$VCS_FILE_NAME$separator"

    private val vcsDir = File(vcsDirPath)

    val config = vcsDir.resolve(CONFIG_FILE_NAME)

    val index = vcsDir.resolve(INDEX_FILE_NAME)

    val log = vcsDir.resolve(LOG_FILE_NAME)

    val commitsDir = vcsDir.resolve(COMMITS_DIR_NAME)

    fun createVCSStructureIfNotExist() {
        if (!vcsDir.exists()) vcsDir.mkdir()
        if (!config.exists()) config.createNewFile()
        if (!index.exists()) index.createNewFile()
        if (!log.exists()) log.createNewFile()
        if (!commitsDir.exists()) commitsDir.mkdir()
    }

    fun getFileByName(fileName: String): File {
        return File(".$separator$fileName")
    }
}
