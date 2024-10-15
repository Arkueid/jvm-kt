package ch03.classfile

interface AttributeInfo {
    fun readInfo(reader: ClassReader)

    companion object {
        @JvmStatic
        fun readAttributes(reader: ClassReader, cp: ConstantPool): Array<AttributeInfo> {
            val attributeCount = reader.readUint16().toInt()
            val attributes = Array(attributeCount) {
                readAttribute(reader, cp)
            }
            return attributes
        }

        @JvmStatic
        fun readAttribute(reader: ClassReader, cp: ConstantPool): AttributeInfo {
            val attrNameIndex = reader.readUint16()
            val attrName = cp.getUtf8(attrNameIndex)
            val attrLen = reader.readUint32()
            return newAttributeInfo(attrName, attrLen, cp).apply {
                readInfo(reader)
            }
        }

        @JvmStatic
        fun newAttributeInfo(attrName: String, attrLen: UInt, cp: ConstantPool): AttributeInfo {
            return when (attrName) {
                "Code" -> CodeAttribute(cp)

                "ConstantValue" -> ConstantValueAttribute()

                "Deprecated" -> DeprecatedAttribute()

                "Exceptions" -> ExceptionsAttribute()

                "LineNumberTable" -> LineNumberTableAttribute()

                "LocalVariableTable" -> LocalVariableTableAttribute()

                "SourceFile" -> SourceFileAttribute(cp)

                "Synthetic" -> SyntheticAttribute()

                else -> UnparsedAttribute(attrName, attrLen.toInt())
            }
        }
    }
}

@OptIn(ExperimentalUnsignedTypes::class)
class UnparsedAttribute(
    var name: String,
    private var length: Int,
) : AttributeInfo {
    private var info: UByteArray? = null

    override fun readInfo(reader: ClassReader) {
        info = reader.readBytes(length)
    }

}


open class MarkerAttribute : AttributeInfo {
    override fun readInfo(reader: ClassReader) {
        // read nothing
    }

}

class DeprecatedAttribute : MarkerAttribute()

class SyntheticAttribute : MarkerAttribute()


class SourceFileAttribute(val cp: ConstantPool) : AttributeInfo {
    var sourceFileIndex: UShort = 0U

    override fun readInfo(reader: ClassReader) {
        sourceFileIndex = reader.readUint16()
    }

    val fileName: String get() = cp.getUtf8(sourceFileIndex)

}


class ConstantValueAttribute : AttributeInfo {

    var constantValueIndex: UShort = 0U

    override fun readInfo(reader: ClassReader) {
        constantValueIndex = reader.readUint16()
    }

}


@OptIn(ExperimentalUnsignedTypes::class)
class CodeAttribute(
    val cp: ConstantPool
) : AttributeInfo {
    // 操作数栈的最大深度
    var maxStack: UShort = 0U

    // 局部变量表大小
    var maxLocals: UShort = 0U

    var code: UByteArray? = null

    lateinit var exceptionTable: Array<ExceptionTableEntry>

    lateinit var attributes: Array<AttributeInfo>

    override fun readInfo(reader: ClassReader) {
        maxStack = reader.readUint16()
        maxLocals = reader.readUint16()
        // TODO: uint32 vs int32
        val codeLength = reader.readUint32().toInt()
        code = reader.readBytes(codeLength)
        exceptionTable = readExceptionTable(reader)
        attributes = AttributeInfo.readAttributes(reader, cp)
    }

    private fun readExceptionTable(reader: ClassReader): Array<ExceptionTableEntry> {
        val length = reader.readUint16().toInt()
        return Array(length) {
            ExceptionTableEntry(
                reader.readUint16(),
                reader.readUint16(),
                reader.readUint16(),
                reader.readUint16()
            )
        }
    }

}


@OptIn(ExperimentalUnsignedTypes::class)
class ExceptionsAttribute : AttributeInfo {
    var exceptionIndexTable: UShortArray? = null

    override fun readInfo(reader: ClassReader) {
        exceptionIndexTable = reader.readUint16s()
    }
}

class LineNumberTableAttribute : AttributeInfo {
    lateinit var lineNumberTable: Array<LineNumberTableEntry>
    override fun readInfo(reader: ClassReader) {
        lineNumberTable = Array(reader.readUint16().toInt()) {
            LineNumberTableEntry(
                reader.readUint16(),
                reader.readUint16()
            )
        }
    }

}

class LocalVariableTableAttribute : AttributeInfo {
    lateinit var localVariableTable: Array<LocalVariableTableEntry>

    override fun readInfo(reader: ClassReader) {
        localVariableTable = Array(reader.readUint16().toInt()) {
            LocalVariableTableEntry(
                reader.readUint16(),
                reader.readUint16(),
                reader.readUint16(),
                reader.readUint16(),
                reader.readUint16()
            )
        }
    }

}

