package ch08.rtdata.heap

import ch08.classfile.ConstantInterfaceMethodRefInfo

class InterfaceMethodRef(rtCp: KvmConstantPool, interfaceMethodRefInfo: ConstantInterfaceMethodRefInfo): KvmMemberRef() {
    init {
        cp = rtCp
        copyMemberRefInfo(interfaceMethodRefInfo)
    }
}