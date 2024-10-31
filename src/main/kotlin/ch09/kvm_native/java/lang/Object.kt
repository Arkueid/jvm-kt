package ch09.kvm_native.java.lang

import ch09.kvm_native.KvmNative
import ch09.rtdata.KvmFrame

object Object {
    @JvmStatic
    fun init() = KvmNative.register("java/lang/Object", "getClass", "()Ljava/lang/Class;", ::getClass)
}

// public final native Class<?> getClass();
private fun getClass(frame: KvmFrame) {
    val _this = frame.localVars.getRef(0u)
    val jCass = _this!!.klass.jClass
    frame.operandStack.pushRef(jCass)
}