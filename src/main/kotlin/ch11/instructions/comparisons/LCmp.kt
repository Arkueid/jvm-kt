package ch11.instructions.comparisons

import ch11.instructions.base.NoOperandsInstruction
import ch11.rtdata.KvmFrame

class LCMP : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val v2 = popLong()
        val v1 = popLong()
        if (v1 > v2) {
            pushInt(1)
        } else if (v1 == v2) {
            pushInt(0)
        } else {
            pushInt(-1)
        }
    }
}