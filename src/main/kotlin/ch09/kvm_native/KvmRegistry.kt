package ch09.kvm_native

import ch09.kvm_native.java.lang.Class
import ch09.kvm_native.java.lang.Object
import ch09.rtdata.KvmFrame

typealias NativeMethod = (KvmFrame) -> Unit

val kvmEmptyNativeMethod: NativeMethod = { /* 在这里什么也不做~ */ }

object KvmNative {
    val kvmRegistry = mutableMapOf<String, NativeMethod>()

    init {
        Class.init()
        Object.init()
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

