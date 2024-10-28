package ch08.rtdata.heap

class KvmObject(
    val klass: KvmClass,
    private val _fields: KvmSlots = KvmSlots(0u),
    val data: Array<out Any?> = emptyArray(),
) {
    val fields get() = _fields

    fun isInstanceOf(klass: KvmClass): Boolean {
        return klass.isAssignableFrom(klass)
    }
}