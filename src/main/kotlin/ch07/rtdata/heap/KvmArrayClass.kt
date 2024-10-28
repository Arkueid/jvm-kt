package ch07.rtdata.heap

val KvmClass.isArray: Boolean get() = name[0] == '['

fun KvmClass.newArray(count: UInt): KvmObject {
    if (!isArray) {
        throw RuntimeException("Not array class: $name")
    }

    val size = count.toInt()
    assert(size >= 0)

    return when (name) {
        "[Z" -> KvmObject(this, data = Array<Byte>(size) { 0 })
        "[B" -> KvmObject(this, data = Array<Byte>(size) { 0 })
        "[C" -> KvmObject(this, data = Array<UShort>(size) { 0u })
        "[S" -> KvmObject(this, data = Array<Short>(size) { 0 })
        "[I" -> KvmObject(this, data = Array<Int>(size) { 0 })
        "[J" -> KvmObject(this, data = Array<Long>(size) { 0 })
        "[F" -> KvmObject(this, data = Array<Float>(size) { 0f })
        "[D" -> KvmObject(this, data = Array<Double>(size) { 0.0 })
        else -> KvmObject(this, data = Array<KvmObject?>(size) { null })
    }
}