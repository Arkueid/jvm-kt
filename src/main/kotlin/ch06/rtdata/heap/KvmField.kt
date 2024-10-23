package ch06.rtdata.heap

import ch06.classfile.KvmClass
import ch06.classfile.MemberInfo

class KvmField : KvmClassMember() {
    var slotId: UInt = 0u

    companion object {
        @JvmStatic
        fun createFields(clazz: KvmClass, fields: Array<MemberInfo>): Array<KvmField> {
            return Array(fields.size) {
                KvmField().apply {
                    this.klass = clazz
                    this.copyMemberInfo(fields[it])
                    this.copyAttributes(fields[it])
                }
            }
        }
    }

    private fun copyAttributes(info: MemberInfo) {
        info.constantValueAttribute?.let {
            constantValueIndex = it.constantValueIndex.toUInt()
        }
    }

    val isLongOrDouble = descriptor == "J" || descriptor == "D"

    var constantValueIndex: UInt = 0u
}