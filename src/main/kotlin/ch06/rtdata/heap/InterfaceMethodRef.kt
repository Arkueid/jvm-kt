package ch06.rtdata.heap

import ch06.classfile.ConstantInterfaceMethodRefInfo

class InterfaceMethodRef(rtCp: KvmConstantPool, interfaceMethodRefInfo: ConstantInterfaceMethodRefInfo): KvmMemberRef() {
    init {
        cp = rtCp
        copyMemberRefInfo(interfaceMethodRefInfo)
    }
}