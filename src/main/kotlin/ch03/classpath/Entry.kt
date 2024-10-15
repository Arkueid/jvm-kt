package ch03.classpath

import java.io.File

val pathListSeparator = File.pathSeparator.toString()

class ReadClassResult(
    var data: ByteArray? = null,
    var entry: Entry? = null,
    var error: Throwable? = null,
)


interface Entry {
    companion object {
        @JvmStatic
        fun create(path: String): Entry {
            if (path.contains(pathListSeparator)) {
                return CompositeEntry(path)
            }

            if (path.endsWith("*")) {
                return WildCardEntry(path)
            }

            if (path.contains(".jar") || path.endsWith(".JAR") || path.endsWith(".zip") || path.endsWith(".ZIP")) {
                return ZipEntry(path)
            }

            return DirEntry(path)
        }
    }

    fun readClass(className: String): ReadClassResult
    fun String(): String
}




