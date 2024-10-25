package ch07.instructions.conversions

import ch07.instructions.base.NoOperandsInstruction
import ch07.rtdata.KvmFrame

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