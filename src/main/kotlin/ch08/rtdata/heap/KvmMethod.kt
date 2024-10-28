package ch08.rtdata.heap

import ch08.classfile.MemberInfo

class KvmMethod : KvmClassMember() {
    var maxStack: UInt = 0u
    var maxLocals: UInt = 0u
    lateinit var code: ByteArray

    companion object {
        @JvmStatic
        fun createMethods(klass: KvmClass, infos: Array<MemberInfo>): Array<KvmMethod> {
            return Array(infos.size) {
                KvmMethod().apply {
                    this.klass = klass
                    this.copyMemberInfo(infos[it])
                    this.copyAttributes(infos[it])
                    this.calcArgSlotCount()
                }
            }
        }
    }

    private fun calcArgSlotCount() {
        val parsedDescriptor = KvmMethodDescriptorParser.parseMethodDescriptor(descriptor)
        parsedDescriptor.parameterTypes.forEach { paramType ->
            _argSlotCount++
            if (paramType == "J" || paramType == "D") {
                _argSlotCount++
            }
        }
        if (!isStatic) { // 自身不是静态方法，则需要实例引用
            _argSlotCount++
        }
    }

    private fun copyAttributes(info: MemberInfo) = info.codeAttribute?.let {
        maxStack = it.maxStack.toUInt()
        maxLocals = it.maxLocals.toUInt()
        code = it.code
    }

    private var _argSlotCount: Int = 0
    val argSlotCount get() = _argSlotCount
}