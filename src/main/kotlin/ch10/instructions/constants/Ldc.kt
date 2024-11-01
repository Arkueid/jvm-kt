package ch10.instructions.constants

import ch10.instructions.base.Index16Instruction
import ch10.instructions.base.Index8Instruction
import ch10.rtdata.KvmFrame
import ch10.rtdata.heap.KvmClassRef
import ch10.rtdata.heap.KvmDouble
import ch10.rtdata.heap.KvmFloat
import ch10.rtdata.heap.KvmInt
import ch10.rtdata.heap.kvmJString
import ch10.rtdata.heap.KvmLong
import ch10.rtdata.heap.KvmString
import ch10.rtdata.heap.getDouble
import ch10.rtdata.heap.getFloat
import ch10.rtdata.heap.getInt
import ch10.rtdata.heap.getLong

private fun _ldc(frame: KvmFrame, index: UInt) {
    val stack = frame.operandStack
    val klass = frame.method.klass
    val c = klass.constantPool.getConstant(index)

    when (c) {
        is KvmInt -> stack.pushInt(c.getInt())
        is KvmFloat -> stack.pushFloat(c.getFloat())
        is KvmString -> {
            val internedStr = kvmJString(klass.loader, c.value)
            stack.pushRef(internedStr)
        }

        is KvmClassRef -> {
            val classObj = c.resolvedClass.jClass
            stack.pushRef(classObj)
        }

        else -> throw RuntimeException("todo: ldc!")
    }
}

class LDC : Index8Instruction() {
    override fun execute(frame: KvmFrame) = _ldc(frame, index)

}

class LDC_W : Index16Instruction() {
    override fun execute(frame: KvmFrame) = _ldc(frame, index)

}

class LDC2_W : Index16Instruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val cp = frame.method.klass.constantPool
        val c = cp.getConstant(index)

        when (c) {
            is KvmLong -> stack.pushLong(c.getLong())
            is KvmDouble -> stack.pushDouble(c.getDouble())
            else -> throw RuntimeException("java.lang.ClassFormatError")
        }
    }
}

