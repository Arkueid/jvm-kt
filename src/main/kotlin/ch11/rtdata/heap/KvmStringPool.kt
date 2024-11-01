package ch11.rtdata.heap

// 字符串池

// map[jvm虚拟机底层实现的string -> java.lang.String 实例]
var internedStrings: MutableMap<String, KvmObject> = mutableMapOf()


// java.lang.String {
//      char[] value // 字符串数组引用 -> [C
//      int hash
// }
fun kvmJString(loader: KvmClassLoader, ktString: String): KvmObject {
    return internedStrings[ktString] ?: run {
        val chars = stringToUtf16(ktString)
        // [C 对应 Kvm 内部数据为 Array<Char>
        val jChars = KvmObject(loader.loadClass("[C"), data = chars)

        val jStr = loader.loadClass("java/lang/String").newObject()
        jStr.setRefVar("value", "[C", jChars)

        internedStrings[ktString] = jStr

        jStr // return
    }
}

private fun stringToUtf16(ktString: String): Array<UShort> {
    return Array(ktString.length) {
        ktString[it].code.toUShort()
    }
}

fun kvmStrFromJStr(jStr: KvmObject?): String? {
    if (jStr == null) return jStr
    val charArr = jStr.getRefVar("value", "[C")
    return charArr.chars.map { Char(it) }.joinToString(separator = "")
}


fun internString(jStr: KvmObject): KvmObject {
    val ktString = kvmStrFromJStr(jStr)!!
    return internedStrings[ktString] ?: run {
        internedStrings[ktString] = jStr
        jStr
    }
}