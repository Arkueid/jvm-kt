package ch11.rtdata.heap

private val _shimClass = KvmClass().apply { name = "~shim" }
private val _returnCode = byteArrayOf(0xb1.toByte()) // return
private val _athrowCode = byteArrayOf(0xbf.toByte()) // athrow

private val _returnMethod = KvmMethod().apply {
    accessFlags = KvmAccessFlags.ACC_STATIC
    name = "<return>"
    klass = _shimClass
    code = _returnCode
}

private val _athrowMethod = KvmMethod().apply {
    accessFlags = KvmAccessFlags.ACC_STATIC
    name = "<athrow>"
    klass = _shimClass
    code = _athrowCode
}


object KvmShimMethod {
    val athrowMethod get() = _athrowMethod
    val returnMethod get() = _returnMethod
}