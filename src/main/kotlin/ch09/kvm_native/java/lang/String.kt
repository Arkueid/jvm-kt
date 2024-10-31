package ch09.kvm_native.java.lang

import ch09.kvm_native.KvmNative
import ch09.rtdata.KvmFrame
import ch09.rtdata.heap.internString

object String {
    @JvmStatic
    fun init() = KvmNative.register(
        "java/lang/String", "intern", "()Ljava/lang/String;", ::intern
    )
}

private fun intern(frame: KvmFrame) {
    val _this = frame.localVars.getRef(0u)!!
    val interned = internString(_this)
    frame.operandStack.pushRef(interned)
}