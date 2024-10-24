package ch06.rtdata.heap

import ch06.classfile.KvmClass

class KvmObject(
    val klass: KvmClass,
    val fields: KvmSlots,
) {

    fun isInstanceOf(klass: KvmClass): Boolean {
        return klass.isAssignableFrom(klass)
    }
}