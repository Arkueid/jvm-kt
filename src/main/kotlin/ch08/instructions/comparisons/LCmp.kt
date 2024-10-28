package ch08.instructions.comparisons

import ch08.instructions.base.NoOperandsInstruction
import ch08.rtdata.KvmFrame

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