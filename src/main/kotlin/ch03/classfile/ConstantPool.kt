package ch03.classfile

import kotlin.UByte

class ConstantPool {
    private var data: Array<ConstantInfo?> = emptyArray()

    fun getConstantInfo(index: UShort): ConstantInfo {
        TODO()
    }

    fun getNameAndType(index: UShort): Array<String> {
        TODO()
    }

    fun getClassName(uShort: UShort): String {
        TODO()
    }

    fun getUtf8(index: UShort): String {
        TODO("Not yet implemented")
    }

    companion object {
        @JvmStatic
        fun readConstantPool(reader: ClassReader): ConstantPool {
            val constantPool = ConstantPool()
            val constantCount = reader.readUint16().toInt()
            val constantInfoList = mutableListOf<ConstantInfo>()
            var i = 1
            while (i <= constantCount) {
                val ci = readConstantInfo(reader, constantPool)
                constantInfoList.add(ci)
                // TODO: 可能有问题，源码见书 P36
                if (ci is ConstantLongInfo || ci is ConstantDoubleInfo) {
                    i += 2
                } else {
                    i++
                }
            }

            constantPool.data = constantInfoList.toTypedArray()
            return constantPool
        }

        @JvmStatic
        fun readConstantInfo(reader: ClassReader, constantPool: ConstantPool): ConstantInfo {
            TODO()
        }

    }
}

interface ConstantInfo {

    companion object {
        const val CONSTANT_CLASS: UByte = 7U
        const val CONSTANT_FIELD_REF: UByte = 9U
        const val CONSTANT_METHOD_REF: UByte = 10U
        const val CONSTANT_INTERFACE_METHOD_REF: UByte = 11U
        const val CONSTANT_STRING: UByte = 8U
        const val CONSTANT_INTEGER: UByte = 3U
        const val CONSTANT_FLOAT: UByte = 4U
        const val CONSTANT_LONG: UByte = 5U
        const val CONSTANT_DOUBLE: UByte = 6U
        const val CONSTANT_NAME_AND_TYPE: UByte = 12U
        const val CONSTANT_UTF8: UByte = 1U
        const val CONSTANT_METHOD_HANDLE: UByte = 15U
        const val CONSTANT_METHOD_TYPE: UByte = 16U
        const val CONSTANT_INVOKE_DYNAMIC: UByte = 18U
    }

    fun readInfo(reader: ClassReader)
}

private fun newConstantInfo(reader: ClassReader, cp: ConstantPool): ConstantInfo {
    val tag = reader.readUint8()
    return when (tag) {
        ConstantInfo.CONSTANT_CLASS -> ConstantClassInfo()
        ConstantInfo.CONSTANT_FIELD_REF -> ConstantFieldRefInfo()
        ConstantInfo.CONSTANT_METHOD_REF -> ConstantMethodRefInfo()
        ConstantInfo.CONSTANT_INTERFACE_METHOD_REF -> ConstantInterfaceMethodRefInfo()
        ConstantInfo.CONSTANT_STRING -> ConstantStringInfo()
        ConstantInfo.CONSTANT_INTEGER -> ConstantIntegerInfo()
        ConstantInfo.CONSTANT_FLOAT -> ConstantFloatInfo()
        ConstantInfo.CONSTANT_LONG -> ConstantLongInfo()
        ConstantInfo.CONSTANT_DOUBLE -> ConstantDoubleInfo()
        ConstantInfo.CONSTANT_NAME_AND_TYPE -> ConstantNameAndTypeInfo()
        ConstantInfo.CONSTANT_UTF8 -> ConstantUtf8Info()
        ConstantInfo.CONSTANT_METHOD_HANDLE -> ConstantMethodHandleInfo()
        ConstantInfo.CONSTANT_METHOD_TYPE -> ConstantMethodTypeInfo()
        ConstantInfo.CONSTANT_INVOKE_DYNAMIC -> ConstantInvokeDynamicInfo()

        else -> throw RuntimeException("java.lang.ClassFormatError: constant pool tag!")
    }
}

class ConstantClassInfo : ConstantInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }
}

class ConstantFieldRefInfo : ConstantInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }
}

class ConstantMethodRefInfo : ConstantInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }
}

class ConstantInterfaceMethodRefInfo : ConstantInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }
}

class ConstantStringInfo : ConstantInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }
}

class ConstantIntegerInfo : ConstantInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }
}

class ConstantFloatInfo : ConstantInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }
}

class ConstantLongInfo : ConstantInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }
}

class ConstantDoubleInfo : ConstantInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }
}

class ConstantNameAndTypeInfo : ConstantInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }
}

class ConstantUtf8Info : ConstantInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }
}

class ConstantMethodHandleInfo : ConstantInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }
}

class ConstantMethodTypeInfo : ConstantInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }
}

class ConstantInvokeDynamicInfo : ConstantInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }
}


