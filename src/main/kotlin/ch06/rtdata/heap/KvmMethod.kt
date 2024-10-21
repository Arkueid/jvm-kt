package ch06.rtdata.heap

import ch06.classfile.KvmClass
import ch06.classfile.MemberInfo

class KvmMethod : KvmClassMember() {
    private var maxStack: UInt = 0u
    private var maxLocals: UInt = 0u
    private lateinit var code: ByteArray

    companion object {
        @JvmStatic
        fun createMethods(klass: KvmClass, infos: Array<MemberInfo>): Array<KvmMethod> {
            return Array(infos.size) {
                KvmMethod().apply {
                    this.klass = klass
                    this.copyMemberInfo(infos[it])
                    this.copyAttributes(infos[it])
                }
            }
        }
    }

    private fun copyAttributes(info: MemberInfo) = info.codeAttribute?.let {
        maxStack = it.maxStack.toUInt()
        maxLocals = it.maxLocals.toUInt()
        code = it.code
    }
}