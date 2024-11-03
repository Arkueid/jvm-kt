package ch11.kvm_native.sun.misc

import ch11.kvm_native.KvmNative
import ch11.rtdata.KvmFrame

object Signal {
    fun init() {
        KvmNative.register("sun/misc/Signal", "findSignal", "(Ljava/lang/String;)I", ::findSignal)
        KvmNative.register("sun/misc/Signal", "handle0", "(IJ)J", ::handle0)
    }
}

private fun findSignal(frame: KvmFrame) {
    val name = frame.localVars.getRef(0u)
    // do something here
    frame.operandStack.pushInt(0)
}

private fun handle0(frame: KvmFrame) {
    frame.operandStack.pushLong(0)
}