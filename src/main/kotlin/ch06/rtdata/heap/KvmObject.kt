package ch06.rtdata.heap

import ch06.classfile.KvmClass

class KvmObject(
    val klass: KvmClass,
    val fields: KvmSlots,
) {
}