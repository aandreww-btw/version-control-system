package command

import svcs.file.FileWorker

class AddCommand : VCSCommand {

    private val fileWorker = FileWorker()

    companion object {
        const val name = "add"
    }

    override fun doAction(fileName: String?) {
        if (fileName.isNullOrEmpty()) {
            val storedFileNames = fileWorker.index.readText()
            if (storedFileNames.isNotEmpty()) {
                println("Tracked files:\n$storedFileNames")
            } else {
                println("Add a file to the index.")
            }
        } else {
            if (fileWorker.getFileByName(fileName).exists()) {
                val isFileTracked = fileWorker.index.readLines().contains(fileName)
                if (!isFileTracked) {
                    fileWorker.index.appendText("$fileName\n")
                }
                println("The file '$fileName' is tracked.")
            } else {
                println("Can't find '$fileName'.")
            }
        }
    }
}
