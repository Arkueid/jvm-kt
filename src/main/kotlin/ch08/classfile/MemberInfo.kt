package ch08.classfile

class MemberInfo(
    var cp: ConstantPool,
    var accessFlags: UShort,
    private var nameIndex: UShort,
    private var descriptionIndex: UShort,
    private var attributes: Array<AttributeInfo>,
) {


    val codeAttribute: CodeAttribute?
        get() {
            for (attr in attributes) {
                if (attr is CodeAttribute) {
                    return attr
                }
            }
            return null
        }

    val constantValueAttribute: ConstantValueAttribute?
        get() {
            for (attr in attributes) {
                if (attr is ConstantValueAttribute) return attr
            }
            return null
        }


    val name: String get() = cp.getUtf8(nameIndex)

    val descriptor: String get() = cp.getUtf8(descriptionIndex)

    companion object {
        @JvmStatic
        fun readMembers(reader: ClassReader, constantPool: ConstantPool): Array<MemberInfo> {
            val memberCount = reader.readUint16().toInt()
            return Array(memberCount) {
                readMember(reader, constantPool)
            }
        }

        @JvmStatic
        fun readMember(reader: ClassReader, constantPool: ConstantPool): MemberInfo {
            return MemberInfo(
                constantPool,
                reader.readUint16(),
                reader.readUint16(),
                reader.readUint16(),
                AttributeInfo.readAttributes(reader, constantPool)
            )
        }
    }
}