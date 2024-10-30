package ch09.rtdata.heap

import ch09.classfile.ConstantClassInfo

class KvmClassRef(rtCp: KvmConstantPool, classInfo: ConstantClassInfo) : KvmSymRef() {
    init {
        cp = rtCp
        className = classInfo.name
    }
}