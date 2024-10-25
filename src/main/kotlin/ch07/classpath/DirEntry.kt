package ch07.classpath

import java.io.File
import java.nio.file.Paths

class DirEntry(path: String) : Entry {
    val absDir: String

    init {
        try {
            absDir = Paths.get(path).toAbsolutePath().toString()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    override fun readClass(className: String): ReadClassResult {
        val result = ReadClassResult()

        try {
            val file = File(absDir, className)
            result.data = file.readBytes()
        } catch (e: Exception) {
            result.data = null
            result.error = e
        }

        result.entry = this

        return result
    }

    override fun String(): String = absDir
}