package ch11.kvm_native.java.lang

import ch11.kvm_native.KvmNative
import ch11.rtdata.KvmFrame

object Double {
    @JvmStatic
    fun init() {
        KvmNative.register("java/lang/Double", "doubleToRawLongBits", "(D)J", ::doubleToRawLongBits)
        KvmNative.register("java/lang/Double", "longBitsToDouble", "(J)D", ::longBitsToDouble)
    }
}

private fun doubleToRawLongBits(frame: KvmFrame) {
    val value = frame.localVars.getDouble(0u)
    val bits = value.toBits()
    frame.operandStack.pushLong(bits)
}

private fun longBitsToDouble(frame: KvmFrame) {
    val value = frame.localVars.getLong(0u)
    val double = value.toDouble()
    frame.operandStack.pushDouble(double)
}