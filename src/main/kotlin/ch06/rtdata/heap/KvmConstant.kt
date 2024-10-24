package ch06.rtdata.heap

import ch06.classfile.ConstantInterfaceMethodRefInfo

class KvmInt(val value: Int) : KvmConstant

class KvmFloat(val value: Float) : KvmConstant

class KvmDouble(val value: Double) : KvmConstant

class KvmLong(val value: Long) : KvmConstant

class KvmString(val value: String) : KvmConstant

class KvmInterfaceMethodRef(rtCp: KvmConstantPool, interfaceMethodRefInfo: ConstantInterfaceMethodRefInfo) :
    KvmConstant