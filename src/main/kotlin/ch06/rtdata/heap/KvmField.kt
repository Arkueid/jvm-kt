package ch06.rtdata.heap

import ch06.classfile.KvmClass
import ch06.classfile.MemberInfo

class KvmField : KvmClassMember() {

    companion object {
        @JvmStatic
        fun createFields(clazz: KvmClass, fields: Array<MemberInfo>): Array<KvmField> {
            return Array(fields.size) {
                KvmField().apply {
                    this.klass = clazz
                    this.copyMemberInfo(fields[it])
                }
            }
        }
    }
}