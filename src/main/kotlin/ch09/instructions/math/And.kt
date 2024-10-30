package ch09.instructions.math

import ch09.instructions.base.NoOperandsInstruction
import ch09.rtdata.KvmFrame

class IAND : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val v2 = stack.popInt()
        val v1 = stack.popInt()
        val res = v1 and v2
        stack.pushInt(res)
    }
}

class LAND : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val v2 = stack.popLong()
        val v1 = stack.popLong()
        val res = v1 and v2
        stack.pushLong(res)
    }
}