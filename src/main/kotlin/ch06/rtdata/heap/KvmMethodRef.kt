package ch06.rtdata.heap

import ch06.classfile.ConstantMethodRefInfo

class KvmMethodRef(rtCp: KvmConstantPool, methodRefInfo: ConstantMethodRefInfo): KvmMemberRef() {
    init {
        cp = rtCp
        copyMemberRefInfo(methodRefInfo)
    }
}