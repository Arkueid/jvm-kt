package ch11.kvm_native.java.util.concurrent.atomic

import ch11.kvm_native.KvmNative
import ch11.rtdata.KvmFrame

object AtomicLong {
    fun init() = KvmNative.register("java/util/concurrent/atomic/AtomicLong",
        "VMSupportsCS8", "()Z", ::vmSupportsCS8)
}

private fun vmSupportsCS8(frame: KvmFrame) {
    frame.operandStack.pushBoolean(false)
}