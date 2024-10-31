package ch09.rtdata.heap

import ch09.classfile.MemberInfo
import ch09.rtdata.heap.KvmMethodDescriptorParser.Companion.parseMethodDescriptor
import kotlin.Byte

class KvmMethod : KvmClassMember() {
    var maxStack: UInt = 0u
    var maxLocals: UInt = 0u
    lateinit var code: ByteArray

    companion object {
        @JvmStatic
        fun newMethods(klass: KvmClass, cfMethods: Array<MemberInfo>): Array<KvmMethod> {
            return Array(cfMethods.size) {
                newMethod(klass, cfMethods[it])
//                    .apply {
//                    this.klass = klass
//                    this.copyMemberInfo(cfMethods[it])
//                    this.copyAttributes(cfMethods[it])
//                    this.calcArgSlotCount()
//                }
            }
        }

        @JvmStatic
        fun newMethod(clazz: KvmClass, cfMethod: MemberInfo): KvmMethod {
            val method = KvmMethod().apply {
                klass = clazz
                copyMemberInfo(cfMethod)
                copyAttributes(cfMethod)
            }
            val md = parseMethodDescriptor(method.descriptor)
            method.calcArgSlotCount(md.parameterTypes)
            if (method.isNative) {
                method.injectCodeAttribute(md.returnType)
            }
            return method
        }
    }

    private fun injectCodeAttribute(returnType: String) {
        maxStack = 4u
        maxLocals = argSlotCount.toUInt()
        when (returnType[0]) {
            'V' -> code = byteArrayOf(0xfe.toByte(), 0xb1.toByte())
            'D' -> code = byteArrayOf(0xfe.toByte(), 0xaf.toByte())
            'F' -> code = byteArrayOf(0xfe.toByte(), 0xae.toByte())
            'J' -> code = byteArrayOf(0xfe.toByte(), 0xad.toByte())
            'L', '[' -> code = byteArrayOf(0xfe.toByte(), 0xb0.toByte())
            else -> code = byteArrayOf(0xfe.toByte(), 0xac.toByte())

        }
    }

    private fun calcArgSlotCount(parameterTypes: List<String>) {
        parameterTypes.forEach { paramType ->
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