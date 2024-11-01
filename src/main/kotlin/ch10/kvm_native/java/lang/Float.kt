package ch10.kvm_native.java.lang

import ch10.kvm_native.KvmNative
import ch10.rtdata.KvmFrame

object Float {
    @JvmStatic
    fun init() = KvmNative.register("java/lang/Float", "floatToRawIntBits", "(F)I", ::floatToRawIntBits)
}

private fun floatToRawIntBits(frame: KvmFrame) {
    val value = frame.localVars.getFloat(0u)
    val bits = value.toBits()
    frame.operandStack.pushInt(bits)
}