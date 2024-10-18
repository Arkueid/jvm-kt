package ch05.classfile

class MemberInfo(
    var cp: ConstantPool,
    var accessFlags: UShort,
    var nameIndex: UShort,
    var descriptionIndex: UShort,
    var attributes: Array<AttributeInfo>? = null,
) {


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