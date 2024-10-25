package ch07.rtdata.heap

import ch07.classfile.KvmClass

class KvmObject(
    val klass: KvmClass,
    val fields: KvmSlots,
) {

    fun isInstanceOf(klass: KvmClass): Boolean {
        return klass.isAssignableFrom(klass)
    }
}