package ch11.rtdata.heap

import ch11.classfile.ConstantClassInfo

class KvmClassRef(rtCp: KvmConstantPool, classInfo: ConstantClassInfo) : KvmSymRef() {
    init {
        cp = rtCp
        className = classInfo.name
    }
}