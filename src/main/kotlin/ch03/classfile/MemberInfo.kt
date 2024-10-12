package ch03.classfile

class MemberInfo {
    var cp: ConstantPool? = null
    var accessFlags: UShort = 0U
    var nameIndex: UShort = 0U
    var descriptionIndex: UShort = 0U
    var attributes: Array<AttributeInfo>? = null

    val name: String get() = cp!!.getUtf8(nameIndex)

    val descriptor: String get() = cp!!.getUtf8(descriptionIndex)

    companion object {
        @JvmStatic
        fun readMembers(reader: ClassReader, constantPool: ConstantPool): Array<MemberInfo> {
            val memberCount = reader.readUint16()
            return Array<MemberInfo>(memberCount.toInt()) {
                readMember(reader, constantPool)
            }
        }

        @JvmStatic
        fun readMember(reader: ClassReader, constantPool: ConstantPool): MemberInfo {
            val mi = MemberInfo()
            mi.cp = constantPool
            mi.accessFlags = reader.readUint16()
            mi.descriptionIndex = reader.readUint16()
            mi.attributes = readAttributes(reader, constantPool)
            return mi
        }

        @JvmStatic
        private fun readAttributes(reader: ClassReader, constantPool: ConstantPool): Array<AttributeInfo> {
            TODO("Not yet implemented")
        }
    }
}