package ch09.instructions.constants

import ch09.instructions.base.Index16Instruction
import ch09.instructions.base.Index8Instruction
import ch09.rtdata.KvmFrame
import ch09.rtdata.heap.KvmDouble
import ch09.rtdata.heap.KvmFloat
import ch09.rtdata.heap.KvmInt
import ch09.rtdata.heap.kvmJString
import ch09.rtdata.heap.KvmLong
import ch09.rtdata.heap.KvmString
import ch09.rtdata.heap.getDouble
import ch09.rtdata.heap.getFloat
import ch09.rtdata.heap.getInt
import ch09.rtdata.heap.getLong

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
        // TODO:
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

