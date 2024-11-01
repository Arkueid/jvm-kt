package ch10.rtdata.heap

import ch10.classfile.ConstantClassInfo

class KvmClassRef(rtCp: KvmConstantPool, classInfo: ConstantClassInfo) : KvmSymRef() {
    init {
        cp = rtCp
        className = classInfo.name
    }
}