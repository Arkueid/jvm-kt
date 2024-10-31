package ch09.kvm_native.java.lang

import ch09.kvm_native.KvmNative
import ch09.rtdata.KvmFrame
import ch09.rtdata.heap.KvmObject
import ch09.rtdata.heap.arrayLength
import ch09.rtdata.heap.componentClass
import ch09.rtdata.heap.isArray
import ch09.rtdata.heap.kvmArrayCopy

object System {
    @JvmStatic
    fun init() =
        KvmNative.register("java/lang/System", "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V", ::arraycopy)

}

// public static native void arraycopy(
// Object src, int srcPos, Object dst, int dstPos, int length
// )
private fun arraycopy(frame: KvmFrame) {
    val vars = frame.localVars
    val src = vars.getRef(0u)
    val srcPos = vars.getInt(1u)
    val dst = vars.getRef(2u)
    val dstPos = vars.getInt(3u)
    val length = vars.getInt(4u)

    if (src == null || dst == null) {
        throw RuntimeException("java.lang.NullPointerException")
    }

    if (!checkArrayCopy(src, dst)) {
        throw RuntimeException("java.lang.ArrayStoreException")
    }

    if (srcPos < 0 || dstPos < 0 || length < 0 ||
        srcPos + length > src.arrayLength ||
        dstPos + length > dst.arrayLength
    ) {
        throw RuntimeException("java.lang.IndexOutOfBoundsException")
    }

    kvmArrayCopy(src, srcPos, dst, dstPos, length)
}

private fun checkArrayCopy(src: KvmObject, dst: KvmObject): Boolean {
    val srcClass = src.klass
    val dstClass = dst.klass

    if (!srcClass.isArray || !dstClass.isArray) {
        return false
    }

    if (srcClass.componentClass.isPrimitive || dstClass.isPrimitive) {
        return dstClass == srcClass
    }

    return true
}

