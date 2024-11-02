package ch11.kvm_native.sun.reflect

import ch11.kvm_native.KvmNative
import ch11.rtdata.KvmFrame

object Reflection {
    fun init() {
        KvmNative.register("sun/reflect/Reflection", "getCallerClass", "()Ljava/lang/Class;", ::getCallerClass)
        KvmNative.register(
            "sun/reflect/Reflection",
            "getClassAccessFlags",
            "(Ljava/lang/Class;)I",
            ::getClassAccessFlags
        )
    }
}

private fun getCallerClass(frame: KvmFrame) {
    val callerFrame = frame.thread.frames[2] // TODO ?
    val callerClass = callerFrame.method.klass.jClass
    frame.operandStack.pushRef(callerClass)
}

private fun getClassAccessFlags(frame: KvmFrame) {
    val vars = frame.localVars
    val type = vars.getRef(0u)!!

    val ktClass = type.extraClass
    val flags = ktClass.accessFlags

    frame.operandStack.pushInt(flags.toInt())
}