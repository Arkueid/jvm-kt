package ch07.instructions.conversions

import ch07.instructions.base.NoOperandsInstruction
import ch07.rtdata.KvmFrame


class F2I: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val float = popFloat()
        pushInt(float.toInt())
    }

}

class F2D: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val float = popFloat()
        pushDouble(float.toDouble())
    }

}

class F2L: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val float = popFloat()
        pushLong(float.toLong())
    }

}