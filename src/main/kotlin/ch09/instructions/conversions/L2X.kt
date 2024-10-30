package ch09.instructions.conversions

import ch09.instructions.base.NoOperandsInstruction
import ch09.rtdata.KvmFrame

class L2D : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val long = popLong()
        pushDouble(long.toDouble())
    }

}

class L2F : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val long = popLong()
        pushFloat(long.toFloat())
    }

}

class L2I : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val long = popLong()
        pushInt(long.toInt())
    }

}