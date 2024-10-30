package ch09.instructions.math

import ch09.instructions.base.NoOperandsInstruction
import ch09.rtdata.KvmFrame

class IXOR : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val v1 = popInt()
        val v2 = popInt()
        pushInt(v1 xor v2)
    }

}

class LXOR : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val v1 = popLong()
        val v2 = popLong()
        pushLong(v1 xor v2)
    }

}