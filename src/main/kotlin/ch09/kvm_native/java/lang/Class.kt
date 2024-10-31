package ch09.kvm_native.java.lang

import ch09.kvm_native.KvmNative
import ch09.rtdata.KvmFrame
import ch09.rtdata.heap.kvmJString
import ch09.rtdata.heap.kvmStrFromJStr

object Class {
    @JvmStatic
    fun init() {
        KvmNative.register(
            "java/lang/Class",
            "getPrimitiveClass",
            "(Ljava/lang/String;)Ljava/lang/Class;",
            ::getPrimitiveClass
        )

        KvmNative.register(
            "java/lang/Class",
            "getName0",
            "()Ljava/lang/String;",
            ::getName0
        )

        KvmNative.register(
            "java/lang/Class",
            "desiredAssertionStatus0",
            "(Ljava/lang/Class;)Z",
            ::desiredAssertionStatus0
        )


    }
}


// static native Class<?> getPrimitiveClass(String name);
private fun getPrimitiveClass(frame: KvmFrame) {
    val nameObj = frame.localVars.getRef(0u)!!
    val name = kvmStrFromJStr(nameObj)!!

    val loader = frame.method.klass.loader
    val jClass = loader.loadClass(name).jClass

    frame.operandStack.pushRef(jClass)
}


// private native String getName0();
private fun getName0(frame: KvmFrame) {
    val _this = frame.localVars.getRef(0u)!!
    val klass = _this.extra

    val name = klass.javaName
    val nameObj = kvmJString(klass.loader, name)

    frame.operandStack.pushRef(nameObj)
}

// TODO: 暂不考虑断言
//  private static native boolean desiredAssertionStatus0(Class<> clazz);
private fun desiredAssertionStatus0(frame: KvmFrame) {
    frame.operandStack.pushInt(0)  // return false
}