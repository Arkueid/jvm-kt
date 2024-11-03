package ch11.kvm_native.sun.io

import ch11.kvm_native.KvmNative
import ch11.rtdata.KvmFrame

object Win32ErrorMode {
    fun init() =
        KvmNative.register("sun/io/Win32ErrorMode", "setErrorMode", "(J)J", ::setErrorMode)
}

private fun setErrorMode(frame: KvmFrame) {
    // TODO
    frame.operandStack.pushLong(0)
}