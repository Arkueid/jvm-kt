package ch08.classpath

import java.io.InputStreamReader
import java.nio.file.Paths
import java.util.zip.ZipFile

class ZipEntry(path: String) : Entry {
    val absPath: String

    init {
        try {
            absPath = Paths.get(path).toAbsolutePath().toString()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    override fun readClass(className: String): ReadClassResult {
        val result = ReadClassResult()
        try {
            val zf = ZipFile(absPath)
            val entry = zf.getEntry(className)
            if (entry != null) {
                zf.getInputStream(entry).use {
                    result.data = it.readBytes()
                }
            } else {
                result.error = RuntimeException("Not found")
            }
            zf.close()
        } catch (e: Exception) {
            result.error = e
        }

        result.entry = this

        return result
    }

    override fun String(): String = absPath
}