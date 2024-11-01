package ch11.kvm_native

import ch11.rtdata.KvmFrame

typealias NativeMethod = (KvmFrame) -> Unit

val kvmEmptyNativeMethod: NativeMethod = { /* 在这里什么也不做~ */ }

object KvmNative {
    val kvmRegistry = mutableMapOf<String, NativeMethod>()

    init {
        ch11.kvm_native.java.lang.Class.init()
        ch11.kvm_native.java.lang.Object.init()
        ch11.kvm_native.java.lang.System.init()
        ch11.kvm_native.java.lang.Float.init()
        ch11.kvm_native.java.lang.String.init()
        ch11.kvm_native.java.lang.Double.init()
        ch11.kvm_native.sun.misc.VM.init()
        ch11.kvm_native.java.lang.Throwable.init()
    }

    fun register(className: String, methodName: String, methodDescriptor: String, method: NativeMethod) {
        val key = "$className~$methodName~$methodDescriptor"
        kvmRegistry[key] = method
    }

    fun findNativeMethod(className: String, methodName: String, methodDescriptor: String): NativeMethod? {
        return kvmRegistry["$className~$methodName~$methodDescriptor"]
            ?: if (methodDescriptor == "()V" && methodName == "registerNatives") {
                kvmEmptyNativeMethod
            } else {
                null
            }
    }
}

