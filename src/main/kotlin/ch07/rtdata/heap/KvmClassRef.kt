package ch07.rtdata.heap

import ch07.classfile.ConstantClassInfo

class KvmClassRef(rtCp: KvmConstantPool, classInfo: ConstantClassInfo) : KvmSymRef() {
    init {
        cp = rtCp
        className = classInfo.name
    }
}