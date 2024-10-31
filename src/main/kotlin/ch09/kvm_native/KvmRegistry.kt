package ch09.kvm_native

import ch09.rtdata.KvmFrame

typealias NativeMethod = (KvmFrame) -> Unit

val kvmEmptyNativeMethod: NativeMethod = { /* 在这里什么也不做~ */ }

object KvmNative {
    val kvmRegistry = mutableMapOf<String, NativeMethod>()

    init {
        ch09.kvm_native.java.lang.Class.init()
        ch09.kvm_native.java.lang.Object.init()
        ch09.kvm_native.java.lang.System.init()
        ch09.kvm_native.java.lang.Float.init()
        ch09.kvm_native.java.lang.String.init()
        ch09.kvm_native.java.lang.Double.init()
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

