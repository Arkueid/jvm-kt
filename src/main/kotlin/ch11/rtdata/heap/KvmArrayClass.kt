package ch11.rtdata.heap

val KvmClass.isArray: Boolean get() = name[0] == '['

fun KvmClass.newArray(count: Int): KvmObject {
    if (!isArray) {
        throw RuntimeException("Not array class: $name")
    }

    return when (name) {
        "[Z" -> KvmObject(this, data = Array<Byte>(count) { 0 })
        "[B" -> KvmObject(this, data = Array<Byte>(count) { 0 })
        "[C" -> KvmObject(this, data = Array<UShort>(count) { 0u })
        "[S" -> KvmObject(this, data = Array<Short>(count) { 0 })
        "[I" -> KvmObject(this, data = Array<Int>(count) { 0 })
        "[J" -> KvmObject(this, data = Array<Long>(count) { 0 })
        "[F" -> KvmObject(this, data = Array<Float>(count) { 0f })
        "[D" -> KvmObject(this, data = Array<Double>(count) { 0.0 })
        else -> KvmObject(this, data = Array<KvmObject?>(count) { null })
    }
}

fun newByteArray(loader: KvmClassLoader, bytes: ByteArray): KvmObject = KvmObject(
    loader.loadClass("[B"), data = bytes.toTypedArray()
)

val KvmClass.componentClass: KvmClass get() = loader.loadClass(getComponentClassName(name))


private fun KvmClass.getComponentClassName(className: String): String {
    if (className[0] == '[') {
        return toClassName(className.substring(1))
    }

    throw RuntimeException("Not Array: $className")
}

fun KvmClass.toClassName(descriptor: String): String {
    if (descriptor[0] == '[') { // array
        return descriptor
    }

    if (descriptor[0] == 'L') { // object ref
        return descriptor.substring(1, descriptor.length - 1)
    }

    for (pair in KvmClass.primitiveTypes) {
        if (pair.value == descriptor) {
            return pair.key
        }
    }

    throw RuntimeException("Invalid descriptor: $descriptor")
}
