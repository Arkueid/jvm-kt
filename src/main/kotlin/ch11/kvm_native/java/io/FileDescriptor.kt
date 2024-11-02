package ch11.kvm_native.java.io

import ch11.kvm_native.KvmNative
import ch11.rtdata.KvmFrame


private const val fd = "java/io/FileDescriptor"

object FileDescriptor {
    fun init() {
        KvmNative.register(fd, "set", "(I)J", ::set)
    }
}

private fun set(frame: KvmFrame) {
    // TODO: hack for the moment
    frame.operandStack.pushLong(0)
}