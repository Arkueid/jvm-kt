package ch05.classfile

interface ConstantInfo {

    companion object {
        const val CONSTANT_CLASS = 7
        const val CONSTANT_FIELD_REF = 9
        const val CONSTANT_METHOD_REF = 10
        const val CONSTANT_INTERFACE_METHOD_REF = 11
        const val CONSTANT_STRING = 8
        const val CONSTANT_INTEGER = 3
        const val CONSTANT_FLOAT = 4
        const val CONSTANT_LONG = 5
        const val CONSTANT_DOUBLE = 6
        const val CONSTANT_NAME_AND_TYPE = 12
        const val CONSTANT_UTF8 = 1
        const val CONSTANT_METHOD_HANDLE = 15
        const val CONSTANT_METHOD_TYPE = 16
        const val CONSTANT_INVOKE_DYNAMIC = 18
    }

    fun readInfo(reader: ClassReader)
}

fun newConstantInfo(reader: ClassReader, cp: ConstantPool): ConstantInfo {
    val tag = reader.readUint8().toInt()
    return when (tag) {
        ConstantInfo.CONSTANT_INTEGER -> ConstantIntegerInfo()
        ConstantInfo.CONSTANT_FLOAT -> ConstantFloatInfo()
        ConstantInfo.CONSTANT_LONG -> ConstantLongInfo()
        ConstantInfo.CONSTANT_DOUBLE -> ConstantDoubleInfo()
        ConstantInfo.CONSTANT_UTF8 -> ConstantUtf8Info()
        ConstantInfo.CONSTANT_STRING -> ConstantStringInfo(cp)

        ConstantInfo.CONSTANT_CLASS -> ConstantClassInfo(cp)
        ConstantInfo.CONSTANT_FIELD_REF -> ConstantFieldRefInfo(cp)
        ConstantInfo.CONSTANT_METHOD_REF -> ConstantMethodRefInfo(cp)
        ConstantInfo.CONSTANT_INTERFACE_METHOD_REF -> ConstantInterfaceMethodRefInfo(cp)

        ConstantInfo.CONSTANT_NAME_AND_TYPE -> ConstantNameAndTypeInfo()
        ConstantInfo.CONSTANT_METHOD_TYPE -> ConstantMethodTypeInfo()
        ConstantInfo.CONSTANT_METHOD_HANDLE -> ConstantMethodHandleInfo()
        ConstantInfo.CONSTANT_INVOKE_DYNAMIC -> ConstantInvokeDynamicInfo()

        else -> throw RuntimeException("java.lang.ClassFormatError: constant pool tag!")
    }
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

@OptIn(ExperimentalUnsignedTypes::class)
class ConstantUtf8Info : ConstantInfo {
    var value: String = ""

    override fun readInfo(reader: ClassReader) {
        val length = reader.readUint16().toInt() // 高位不会被符号填充
        val data = reader.readBytes(length)
        value = data.toString(Charsets.UTF_8)
    }
}

class ConstantStringInfo(var cp: ConstantPool) : ConstantInfo {
    var index: UShort = 0U

    override fun readInfo(reader: ClassReader) {
        index = reader.readUint16()
    }

    fun String() = cp.getUtf8(index)
}

class ConstantClassInfo(val cp: ConstantPool) : ConstantInfo {
    var nameIndex: UShort = 0U

    override fun readInfo(reader: ClassReader) {
        nameIndex = reader.readUint16()
    }

    val name: String get() = cp.getUtf8(nameIndex)
}

class ConstantNameAndTypeInfo : ConstantInfo {
    var nameIndex: UShort = 0U
    var descriptorIndex: UShort = 0U
    override fun readInfo(reader: ClassReader) {
        nameIndex = reader.readUint16()
        descriptorIndex = reader.readUint16()
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
    var referenceKind: UByte = 0U
    var referenceIndex: UShort = 0U

    override fun readInfo(reader: ClassReader) {
        referenceKind = reader.readUint8()
        referenceIndex = reader.readUint16()
    }
}

class ConstantMethodTypeInfo : ConstantInfo {
    var descriptorIndex: UShort = 0U

    override fun readInfo(reader: ClassReader) {
        descriptorIndex = reader.readUint16()
    }
}

class ConstantInvokeDynamicInfo : ConstantInfo {
    var bootstrapMethodAttrIndex: UShort = 0U
    var nameAndTypeIndex: UShort = 0U

    override fun readInfo(reader: ClassReader) {
        bootstrapMethodAttrIndex = reader.readUint16()
        nameAndTypeIndex = reader.readUint16()
    }
}


