package ch07.instructions.math

import ch07.instructions.base.NoOperandsInstruction
import ch07.rtdata.KvmFrame

class IOR : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val v2 = popInt()
        val v1 = popInt()
        pushInt(v1 or v2)
    }

}

class LOR : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val v2 = popLong()
        val v1 = popLong()
        pushLong(v1 or v2)
    }

}