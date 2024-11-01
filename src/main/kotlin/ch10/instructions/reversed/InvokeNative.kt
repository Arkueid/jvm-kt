package ch10.instructions.reversed

import ch10.instructions.base.NoOperandsInstruction
import ch10.kvm_native.KvmNative
import ch10.rtdata.KvmFrame

class INVOKE_NATIVE : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val method = frame.method
        val className = method.klass.name
        val methodName = method.name
        val methodDescriptor = method.descriptor

        val nativeMethod = KvmNative.findNativeMethod(className, methodName, methodDescriptor)
        if (nativeMethod == null) {
            val methodInfo = "$className.$methodName$methodDescriptor"
            throw RuntimeException("java.lang.UnsatisfiedLinkError: $methodInfo")
        }

        nativeMethod(frame)
    }

}