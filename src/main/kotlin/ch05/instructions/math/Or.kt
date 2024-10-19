package ch05.instructions.math

import ch05.instructions.base.NoOperandsInstruction
import ch05.rtdata.KvmFrame

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