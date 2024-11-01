package ch10.rtdata.heap

class KvmStackTraceElement(
    val fileName: String,
    val className: String,
    val methodName: String,
    val lineNumber: Int,
) {
    fun String(): String = "$className.$methodName($fileName:$lineNumber)"
}