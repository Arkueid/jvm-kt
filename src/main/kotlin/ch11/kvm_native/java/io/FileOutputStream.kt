package ch11.kvm_native.java.io

import ch11.kvm_native.KvmNative
import ch11.rtdata.KvmFrame
import ch11.rtdata.heap.bytes

object FileOutputStream {
    fun init() {
        KvmNative.register("java/io/FileOutputStream", "writeBytes", "([BIIZ)V", ::writeBytes)
    }
}

// private native void writeBytes(byte b[], int off, int len, boolean append);
private fun writeBytes(frame: KvmFrame) {
    val vars = frame.localVars
    // vars = [this, b, off, len, append]
    val b = vars.getRef(1u)!!
    val off = vars.getInt(2u)
    val len = vars.getInt(3u)

    val jBytes = b.bytes.sliceArray(off..off + len - 1)

    // what a glorious jump
    System.out.writeBytes(jBytes.toByteArray())
}
