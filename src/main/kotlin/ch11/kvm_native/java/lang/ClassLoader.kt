package ch11.kvm_native.java.lang

import ch11.kvm_native.KvmNative
import ch11.rtdata.KvmFrame
import ch11.rtdata.heap.kvmStrFromJStr

object ClassLoader {
    fun init() {
        KvmNative.register(
            "java/lang/ClassLoader",
            "findBuiltinLib",
            "(Ljava/lang/String;)Ljava/lang/String;",
            ::findBuiltinLib
        )

        KvmNative.register(
            "java/lang/ClassLoader\$NativeLibrary",
            "load",
            "(Ljava/lang/String;ZZ)V",
            ::load
        )
    }
}

private fun findBuiltinLib(frame: KvmFrame) {
    val jStr = frame.localVars.getRef(0u)
    val kStr = kvmStrFromJStr(jStr)
    // TODO
    frame.operandStack.pushRef(null)
}

private fun load(frame: KvmFrame) {
    val name = frame.localVars.getRef(0u)
    val path = kvmStrFromJStr(name)
//    System.loadLibrary(path)
}