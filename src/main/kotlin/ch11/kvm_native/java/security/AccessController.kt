package ch11.kvm_native.java.security

import ch11.instructions.base.invokeMethod
import ch11.kvm_native.KvmNative
import ch11.rtdata.KvmFrame

object AccessController {
    fun init() {
        KvmNative.register(
            "java/security/AccessController",
            "doPrivileged",
            "(Ljava/security/PrivilegedAction;)Ljava/lang/Object;",
            ::doPrivileged
        )
        KvmNative.register(
            "java/security/AccessController",
            "doPrivileged",
            "(Ljava/security/PrivilegedExceptionAction;)Ljava/lang/Object;",
            ::doPrivileged
        )
        KvmNative.register(
            "java/security/AccessController",
            "getStackAccessControlContext",
            "()Ljava/security/AccessControlContext;",
            ::getStackAccessControlContext
        )
    }
}

private fun doPrivileged(frame: KvmFrame) {
    val vars = frame.localVars
    val action = vars.getRef(0u)!!

    val stack = frame.operandStack
    stack.pushRef(action)

    val method = action.klass.getInstanceMethod("run", "()Ljava/lang/Object;")!! // TODO
    invokeMethod(frame, method)
}

private fun getStackAccessControlContext(frame: KvmFrame) {
    // TODO: hack for the moment
    //  push null for the first time so far -V-
    frame.operandStack.pushRef(null)
}

