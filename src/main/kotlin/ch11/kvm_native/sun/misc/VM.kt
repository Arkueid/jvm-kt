package ch11.kvm_native.sun.misc

import ch11.instructions.base.invokeMethod
import ch11.kvm_native.KvmNative
import ch11.rtdata.KvmFrame

object VM {
    @JvmStatic
    fun init() {
        KvmNative.register("sun/misc/VM", "initialize", "()V", ::initialize)
    }
}

// private static native void initialize();
private fun initialize(frame: KvmFrame) {
    val classLoader = frame.method.klass.loader
    val jlSysClass = classLoader.loadClass("java/lang/System")
    val initMethod = jlSysClass.getStaticMethod("initializeSystemClass", "()V")!!
    invokeMethod(frame, initMethod)
}