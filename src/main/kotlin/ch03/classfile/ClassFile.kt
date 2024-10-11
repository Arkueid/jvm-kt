package ch03.classfile

@OptIn(ExperimentalUnsignedTypes::class)
class ClassFile {
    //magic uint32
    private var _minorVersion: UShort = 0U
    val minorVersion get() = _minorVersion

    private var _majorVersion: UShort = 0U
    val majorVersion get() = _majorVersion

    private var _constantPool: ConstantPool? = null
    val constantPool get() = _constantPool

    private var _accessFlags: UShort = 0U
    val accessFlags get() = _accessFlags

    private var _thisClass: UShort = 0U

    private var _superClass: UShort = 0U

    private var _interfaces: UShortArray? = null

    private var _fields: Array<MemberInfo>? = null
    val fields get() = _fields

    private var _methods: Array<MemberInfo>? = null
    val methods get() = _methods

    private var _attributes: Array<AttributeInfo>? = null
    val attributes get() = _attributes

    private fun read(reader: ClassReader) {
        readAndCheckMagic(reader)
        readAndCheckVersion(reader)
        _constantPool = readConstantPool(reader)
        _accessFlags = reader.readUint16()
        _thisClass = reader.readUint16()
        _superClass = reader.readUint16()
        _interfaces = reader.readUint16s()
        _fields = readMembers(reader, _constantPool!!)
        _methods = readMembers(reader, _constantPool!!)
        _attributes = readAttributes(reader, _constantPool!!)
    }

    private fun readAttributes(reader: ClassReader, pool: ConstantPool): Array<AttributeInfo> {
        TODO()
    }

    private fun readMembers(reader: ClassReader, pool: ConstantPool): Array<MemberInfo> {
        TODO("Not yet implemented")
    }

    private fun readConstantPool(reader: ClassReader): ConstantPool {
        TODO("Not yet implemented")
    }

    private fun readAndCheckMagic(reader: ClassReader) {
        TODO()
    }

    private fun readAndCheckVersion(reader: ClassReader) {
        TODO()
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
            TODO()
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


class ConstantPool {
    fun getClassName(uShort: UShort): String {
        TODO()
    }
}

class MemberInfo
class AttributeInfo

class ParseClassFileResult(
    var classFile: ClassFile? = null,
    var error: Throwable? = null,
)


