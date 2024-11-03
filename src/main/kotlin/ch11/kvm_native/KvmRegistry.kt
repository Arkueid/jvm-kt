package ch11.kvm_native

import ch11.rtdata.KvmFrame

typealias NativeMethod = (KvmFrame) -> Unit

val kvmEmptyNativeMethod: NativeMethod = { /* 在这里什么也不做~ */ }

object KvmNative {
    private val kvmRegistry = mutableMapOf<String, NativeMethod>()

    init {
        ch11.kvm_native.java.lang.Class.init()
        ch11.kvm_native.java.lang.Object.init()
        ch11.kvm_native.java.lang.System.init()
        ch11.kvm_native.java.lang.Float.init()
        ch11.kvm_native.java.lang.String.init()
        ch11.kvm_native.java.lang.Double.init()
        ch11.kvm_native.sun.misc.VM.init()
        ch11.kvm_native.java.lang.Throwable.init()
        ch11.kvm_native.java.io.FileOutputStream.init()
        ch11.kvm_native.sun.misc.Unsafe.init()
        ch11.kvm_native.sun.reflect.Reflection.init()
        ch11.kvm_native.java.io.FileDescriptor.init()
        ch11.kvm_native.java.security.AccessController.init()
        ch11.kvm_native.java.lang.Thread.init()
        ch11.kvm_native.sun.reflect.NativeConstructorAccessorImpl.init()
        ch11.kvm_native.java.util.concurrent.atomic.AtomicLong.init()
        ch11.kvm_native.java.lang.Runtime.init()
        ch11.kvm_native.java.lang.ClassLoader.init()
        ch11.kvm_native.java.io.WinNTFileSystem.init()
        ch11.kvm_native.sun.misc.Signal.init()
        ch11.kvm_native.sun.io.Win32ErrorMode.init()
    }

    fun register(className: String, methodName: String, methodDescriptor: String, method: NativeMethod) {
        val key = "$className~$methodName~$methodDescriptor"
        kvmRegistry[key] = method
    }

    fun findNativeMethod(className: String, methodName: String, methodDescriptor: String): NativeMethod? {
        return kvmRegistry["$className~$methodName~$methodDescriptor"]
            ?: run {
                if (methodDescriptor == "()V") {
                    // TODO: hack
                    if (methodName == "registerNatives" || methodName == "initIDs") {
                        return kvmEmptyNativeMethod
                    }
                }
                return null
            }


    }
}

