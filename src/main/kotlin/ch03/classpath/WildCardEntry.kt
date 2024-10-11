package ch03.classpath

import java.io.File

class WildCardEntry(path: String) : Entry {
    val entries: List<Entry>

    init {
        val list = mutableListOf<Entry>()
        val baseDir = path.subSequence(0, path.length - 1).toString()
        File(baseDir).listFiles { file ->
            if (file.isFile && file.path.let { it.endsWith(".jar") || it.endsWith(".JAR") }) {

                list.add(ZipEntry(file.path))
                return@listFiles true
            }
            return@listFiles false
        }
        entries = list
    }

    override fun readClass(className: String): ReadClassResult {
        for (entry in entries) {
            val result = entry.readClass(className)
            if (result.error == null) {
                return result
            }
        }

        return ReadClassResult(null, null, RuntimeException(""))
    }

    override fun String(): String = entries.map { it.String() }.joinToString(pathListSeparator)
}