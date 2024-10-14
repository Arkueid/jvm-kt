package ch03.classfile

interface AttributeInfo {
    fun readInfo(reader: ClassReader)

    companion object {
        @JvmStatic
        fun readAttributes(reader: ClassReader, cp: ConstantPool): Array<AttributeInfo> {
            val attributeCount = reader.readUint16().toInt()
            val attributes = Array<AttributeInfo>(attributeCount) {
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
                "Code" -> CodeAttribute()

                "ConstantValue" -> ConstantValueAttribute()

                "Deprecated" -> DeprecatedAttribute()

                "Exceptions" -> ExceptionsAttribute()

                "LineNumberTable" -> LineNumberTableAttribute()

                "LocalVariableTable" -> LocalVariableTableAttribute()

                "SourceFile" -> SourceFileAttribute()

                "Synthetic" -> SyntheticAttribute()

                else -> UnparsedAttribute()
            }
        }
    }
}

class CodeAttribute : AttributeInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }

}

class ConstantValueAttribute : AttributeInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }

}

class DeprecatedAttribute : AttributeInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }

}

class ExceptionsAttribute : AttributeInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }

}

class LineNumberTableAttribute : AttributeInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }

}

class LocalVariableTableAttribute : AttributeInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }

}

class SourceFileAttribute : AttributeInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }

}

class SyntheticAttribute : AttributeInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }

}

class UnparsedAttribute : AttributeInfo {
    override fun readInfo(reader: ClassReader) {
        TODO("Not yet implemented")
    }

}