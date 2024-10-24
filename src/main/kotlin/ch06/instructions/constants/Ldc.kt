package ch06.instructions.constants

import ch06.instructions.base.Index16Instruction
import ch06.instructions.base.Index8Instruction
import ch06.rtdata.KvmFrame
import ch06.rtdata.heap.KvmDouble
import ch06.rtdata.heap.KvmFloat
import ch06.rtdata.heap.KvmInt
import ch06.rtdata.heap.KvmLong
import ch06.rtdata.heap.getDouble
import ch06.rtdata.heap.getFloat
import ch06.rtdata.heap.getInt
import ch06.rtdata.heap.getLong

private fun _ldc(frame: KvmFrame, index: UInt) {
    val stack = frame.operandStack
    val cp = frame.method.klass.constantPool
    val c = cp.getConstant(index)

    when (c) {
        is KvmInt -> stack.pushInt(c.getInt())
        is KvmFloat -> stack.pushFloat(c.getFloat())
        // TODO:
        //  is KvmString ->
        //  is KvmClassRef ->
        else -> throw RuntimeException("todo: ldc!")
    }
}

class LDC : Index8Instruction() {
    override fun execute(frame: KvmFrame) {
        _ldc(frame, index)
    }
}

class LDC_W : Index16Instruction() {
    override fun execute(frame: KvmFrame) {
        _ldc(frame, index)
    }
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

