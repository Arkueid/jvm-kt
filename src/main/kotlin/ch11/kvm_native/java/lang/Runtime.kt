package ch11.kvm_native.java.lang

import ch11.kvm_native.KvmNative
import ch11.rtdata.KvmFrame
import java.lang.Runtime


object Runtime {
    fun init() = KvmNative.register("java/lang/Runtime", "availableProcessors", "()I", ::availableProcessors)
}

private fun availableProcessors(frame: KvmFrame) {
    // well, what can I say?
    val numCPU = Runtime.getRuntime().availableProcessors()

    frame.operandStack.pushInt(numCPU)
}