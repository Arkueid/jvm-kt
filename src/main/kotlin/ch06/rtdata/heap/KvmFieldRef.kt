package ch06.rtdata.heap

import ch06.classfile.ConstantFieldRefInfo

class KvmFieldRef(rtCp: KvmConstantPool, fieldRefInfo: ConstantFieldRefInfo) : KvmSymRef() {
    init {
        cp = rtCp
        className = fieldRefInfo.className

    }
}