package ch02.classpath

open class CompositeEntry(pathList: String) : Entry {
    val entries: List<Entry> = pathList.split(pathListSeparator).map { Entry.create(it) }

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