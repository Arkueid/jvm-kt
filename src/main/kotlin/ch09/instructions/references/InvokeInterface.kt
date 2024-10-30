package ch09.instructions.references

import ch09.instructions.base.BytecodeReader
import ch09.instructions.base.Instruction
import ch09.instructions.base.invokeMethod
import ch09.rtdata.KvmFrame
import ch09.rtdata.heap.KvmInterfaceMethodRef
import ch09.rtdata.heap.isImplements
import ch09.rtdata.heap.lookupMethodInClass

class INVOKE_INTERFACE : Instruction {
    private var index: UInt = 0u

    override fun fetchOperands(reader: BytecodeReader) {
        index = reader.readUint16().toUInt()
        // argSlotCount 可以直接计算得到，由于历史原因被保留
        reader.readUint8() // count
        // 为某些虚拟机的实现使用
        reader.readUint8() // must be 0
    }

    override fun execute(frame: KvmFrame) {
        val cp = frame.method.klass.constantPool
        val methodRef = cp.getConstant(index) as KvmInterfaceMethodRef
        val resolvedMethod = methodRef.resolvedInterfaceMethod
        if (resolvedMethod.isStatic || resolvedMethod.isPrivate) {
            throw RuntimeException("java.lang.IncompatibleClassChangeError")
        }

        val ref = frame.operandStack.getRefFromTop(resolvedMethod.argSlotCount - 1)
        if (ref == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }
        if (!ref.klass.isImplements(methodRef.resolvedClass)) {
            throw RuntimeException("java.lang.IncompatibleClassChangeError")
        }

        val methodToBeInvoked = lookupMethodInClass(ref.klass, methodRef.name, methodRef.descriptor)
        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract) {
            throw RuntimeException("java.lang.AbstractMethodError")
        }
        if (!methodToBeInvoked.isPublic) {
            throw RuntimeException("java.lang.IllegalAccessError")
        }
        invokeMethod(frame, methodToBeInvoked)
    }

}