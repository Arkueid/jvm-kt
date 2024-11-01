package ch10.classfile

@OptIn(ExperimentalUnsignedTypes::class)
class ClassFile {
    // 文件最开头的魔数，由于识别 .class 文件
    // val magic: UInt = 0xcafebabe // Cafe Babe~

    private var _minorVersion: UShort = 0U
    val minorVersion get() = _minorVersion

    private var _majorVersion: UShort = 0U
    val majorVersion get() = _majorVersion

    private var _constantPool: ConstantPool? = null
    val constantPool get() = _constantPool!!

    // 访问标志位，16位的 bit mask，每一位可以表示：文件是类还是接口、访问级别是public还是private...
    private var _accessFlags: UShort = 0U
    val accessFlags get() = _accessFlags

    private var _thisClass: UShort = 0U

    private var _superClass: UShort = 0U

    private var _interfaces: UShortArray? = UShortArray(0)

    private var _fields: Array<MemberInfo> = emptyArray()
    val fields get() = _fields

    private var _methods: Array<MemberInfo> = emptyArray()
    val methods get() = _methods

    private var _attributes: Array<AttributeInfo> = emptyArray()
    val attributes get() = _attributes

    val sourceFileAttribute: SourceFileAttribute? get() {
        attributes.forEach { attr ->
            if (attr is SourceFileAttribute) {
                return attr
            }
        }
        return null
    }

    private fun read(reader: ClassReader) {
        readAndCheckMagic(reader)
        readAndCheckVersion(reader)
        _constantPool = readConstantPool(reader)
        _accessFlags = reader.readUint16()
        // 该类在常量池的索引，用于获取类名等信息
        _thisClass = reader.readUint16()
        // 超类在常量池中的索引，只可能在java.lang.Object中为0
        _superClass = reader.readUint16()
        // 接口的常量池索引，给出该类所实现的接口的名字
        _interfaces = reader.readUint16s()
        _fields = readMembers(reader, _constantPool!!)
        _methods = readMembers(reader, _constantPool!!)
        _attributes = readAttributes(reader, _constantPool!!)
    }

    private fun readAttributes(reader: ClassReader, pool: ConstantPool): Array<AttributeInfo> {
        return AttributeInfo.readAttributes(reader, pool)
    }

    private fun readMembers(reader: ClassReader, pool: ConstantPool): Array<MemberInfo> {
        return MemberInfo.readMembers(reader, pool)
    }

    private fun readConstantPool(reader: ClassReader): ConstantPool = ConstantPool.readConstantPool(reader)

    private fun readAndCheckMagic(reader: ClassReader) {
        val magic = reader.readUint32()
        if (magic.toLong() != 0xCAFEBABE) {
            throw RuntimeException("java.class.ClassFormatError: magic!")
        }
    }

    private fun readAndCheckVersion(reader: ClassReader) {
        _minorVersion = reader.readUint16()
        _majorVersion = reader.readUint16()
        when (_majorVersion.toInt()) {
            45 -> {
                return
            }

            46, 47, 48, 49, 50, 51, 52 -> {
                if (_minorVersion.toInt() == 0) {
                    return
                }
            }
        }

        throw RuntimeException("java.lang.UnsupportedClassVersionError!")
    }

    val className: String
        get() {
            return _constantPool!!.getClassName(_thisClass)
        }

    val superClassName: String
        get() {
            if (_superClass > 0U) {
                return _constantPool!!.getClassName(_superClass)
            }

            return ""  // 只有 java.lang.Object 没有超类
        }

    val interfaceNames: Array<String>
        get() {
            return Array(_interfaces!!.size) { _constantPool!!.getClassName(_interfaces!![it]) }
        }

    companion object {
        @JvmStatic
        fun parse(classData: ByteArray): ParseClassFileResult {
            val result = ParseClassFileResult()
            try {
                val cr = ClassReader(classData)
                val cf = ClassFile()
                cf.read(cr)
                result.classFile = cf
            } catch (e: Exception) {
                result.error = e
            }
            return result
        }
    }
}

class ParseClassFileResult(
    var classFile: ClassFile? = null,
    var error: Throwable? = null,
)


