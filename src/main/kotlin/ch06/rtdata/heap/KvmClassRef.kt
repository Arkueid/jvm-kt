package ch06.rtdata.heap

import ch06.classfile.ConstantClassInfo

class KvmClassRef(rtCp: KvmConstantPool, classInfo: ConstantClassInfo) : KvmSymRef() {
    init {
        cp = rtCp
        className = classInfo.name
    }
}