package ch07.rtdata.heap

class KvmMethodDescriptorParser(
) {
    lateinit var raw: String
    var offset: Int = 0
    lateinit var parsed: KvmMethodDescriptor

    companion object {
        @JvmStatic
        fun parseMethodDescriptor(descriptor: String): KvmMethodDescriptor {
            val parser = KvmMethodDescriptorParser()
            return parser.parse(descriptor)
        }
    }

    private fun parse(descriptor: String): KvmMethodDescriptor {
        raw = descriptor
        parsed = KvmMethodDescriptor()
        startParams()
        parseParamTypes()
        endParams()
        parseReturnType()
        finish()
        return parsed
    }

    private fun startParams() {
        if (readChar() != '(') {
            causePanic()
        }
    }

    private fun parseParamTypes() {
        while (true) {
            val t = parseFieldType()
            if (t != "") {
                parsed.addParameterType(t)
            } else {
                break
            }
        }
    }

    private fun endParams() {
        if (readChar() != ')') {
            causePanic()
        }
    }

    private fun parseReturnType() {
        if (readChar() == 'V') { // --> void
            parsed.returnType = "V"
            return
        }

        unreadChar()
        val t = parseFieldType()
        if (t != "") {
            parsed.returnType = t
            return
        }

        // 无法解析返回类型
        causePanic()
    }

    private fun finish() {
        if (offset != raw.length) {
            causePanic()
        }
    }

    private fun readChar(): Char {
        val c = raw[offset]
        offset++
        return c
    }

    private fun unreadChar() {
        offset--
    }

    private fun causePanic() {
        throw RuntimeException("BAD descriptor: $raw")
    }

    private fun parseFieldType(): String {
        return when (readChar()) {
            'B' -> "B"
            'C' -> "C"
            'D' -> "D"
            'J' -> "J"
            'S' -> "S"
            'Z' -> "Z"
            'L' -> parseObjectType()
            '[' -> parseArrayType()
            else -> {
                unreadChar()
                ""
            }
        }
    }

    private fun parseObjectType(): String {
        val unread = raw.substring(offset)
        val semicolonIndex = unread.indexOfFirst { it == ';' }
        if (semicolonIndex == -1) {
            return ""
        }

        // example: String[] --> [Ljava/lang/String;
        val objStart = offset - 1 // L需要保留
        val objEnd = offset + semicolonIndex + 1
        offset = objEnd
        // example: Ljava/lang/String;
        val descriptor = raw.substring(objStart, objEnd)
        return descriptor
    }

    private fun parseArrayType(): String {
        val arrStart = offset - 1 // 保留 '['
        parseFieldType()
        val arrEnd = offset
        return raw.substring(arrStart, arrEnd)
    }
}
