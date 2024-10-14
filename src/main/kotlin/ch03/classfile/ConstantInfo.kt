package ch03.classfile

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
        ConstantInfo.CONSTANT_CLASS -> ConstantClassInfo(cp)
        ConstantInfo.CONSTANT_FIELD_REF -> ConstantFieldRefInfo(cp)
        ConstantInfo.CONSTANT_METHOD_REF -> ConstantMethodRefInfo(cp)
        ConstantInfo.CONSTANT_INTERFACE_METHOD_REF -> ConstantInterfaceMethodRefInfo(cp)
        ConstantInfo.CONSTANT_STRING -> ConstantStringInfo(cp)
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

class ConstantClassInfo(val cp: ConstantPool) : ConstantInfo {
    var nameIndex: UShort = 0U

    override fun readInfo(reader: ClassReader) {
        nameIndex = reader.readUint16()
    }

    val name: String get() = cp.getUtf8(nameIndex)
}

class ConstantStringInfo(var cp: ConstantPool) : ConstantInfo {
    var index: UShort = 0U

    override fun readInfo(reader: ClassReader) {
        index = reader.readUint16()
    }

    fun String() = cp.getUtf8(index)
}

class ConstantIntegerInfo : ConstantInfo {
    var value: Int = 0

    override fun readInfo(reader: ClassReader) {
        value = reader.readUint32().toInt()
    }
}

class ConstantFloatInfo : ConstantInfo {
    var value: Float = 0f
    override fun readInfo(reader: ClassReader) {
        value = Float.fromBits(reader.readUint32().toInt())
    }
}

class ConstantLongInfo : ConstantInfo {
    var value: Long = 0L

    override fun readInfo(reader: ClassReader) {
        value = reader.readUint64().toLong()
    }
}

class ConstantDoubleInfo : ConstantInfo {
    var value: Double = 0.0

    override fun readInfo(reader: ClassReader) {
        value = Double.fromBits(reader.readUint64().toLong())
    }
}

class ConstantNameAndTypeInfo : ConstantInfo {
    var nameIndex: UShort = 0U
    var descriptorIndex: UShort = 0U
    override fun readInfo(reader: ClassReader) {
        nameIndex = reader.readUint16()
        descriptorIndex = reader.readUint16()
    }
}

class ConstantUtf8Info : ConstantInfo {
    var value: String = ""

    override fun readInfo(reader: ClassReader) {
        val length = reader.readUint16().toInt() // 高位不会被符号填充
        val data = reader.readBytes(length)
        value = data.toString(Charsets.UTF_8)
    }
}

open class ConstantMemberRefInfo(val cp: ConstantPool) : ConstantInfo {
    var classIndex: UShort = 0U
    var nameAndTypeIndex: UShort = 0U

    override fun readInfo(reader: ClassReader) {
        classIndex = reader.readUint16()
        nameAndTypeIndex = reader.readUint16()
    }

    val className get() = cp.getClassName(classIndex)

    val nameAndDescriptor get() = cp.getNameAndType(nameAndTypeIndex)
}

class ConstantFieldRefInfo(cp: ConstantPool) : ConstantMemberRefInfo(cp)

class ConstantMethodRefInfo(cp: ConstantPool) : ConstantMemberRefInfo(cp)

class ConstantInterfaceMethodRefInfo(cp: ConstantPool) : ConstantMemberRefInfo(cp)


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


