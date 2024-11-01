package ch10.kvm_native.sun.misc

import ch10.instructions.base.invokeMethod
import ch10.rtdata.heap.kvmJString
import ch10.kvm_native.KvmNative
import ch10.rtdata.KvmFrame

object VM {
    @JvmStatic
    fun init() {
        KvmNative.register("sun/misc/VM", "initialize", "()V", ::initialize)
    }
}

// private static native void initialize();
private fun initialize(frame: KvmFrame) {
    val vmClass = frame.method.klass
    val savedProps = vmClass.getRefVar("savedProps", "Ljava/util/Properties;")
    val key = kvmJString(vmClass.loader, "foo")
    val value = kvmJString(vmClass.loader, "bar")

    frame.operandStack.pushRef(savedProps)
    frame.operandStack.pushRef(key)
    frame.operandStack.pushRef(value)

    val propsClass = vmClass.loader.loadClass("java/util/Properties")
    val setProperty =
        propsClass.getInstanceMethod("setProperty", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;")!!

    invokeMethod(frame, setProperty)
}