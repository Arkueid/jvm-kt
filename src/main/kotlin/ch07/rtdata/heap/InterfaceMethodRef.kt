package ch07.rtdata.heap

import ch07.classfile.ConstantInterfaceMethodRefInfo

class InterfaceMethodRef(rtCp: KvmConstantPool, interfaceMethodRefInfo: ConstantInterfaceMethodRefInfo): KvmMemberRef() {
    init {
        cp = rtCp
        copyMemberRefInfo(interfaceMethodRefInfo)
    }
}