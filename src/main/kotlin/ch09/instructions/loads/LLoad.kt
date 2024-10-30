package ch09.instructions.loads

import ch09.instructions.base.Index8Instruction
import ch09.instructions.base.NoOperandsInstruction
import ch09.rtdata.KvmFrame

private fun lLoad(frame: KvmFrame, index: UInt) = frame.operandStack.run {
    pushLong(frame.localVars.getLong(index))
}

class LLOAD: Index8Instruction() {
    override fun execute(frame: KvmFrame) {
        lLoad(frame, index)
    }

}

class LLOAD_0: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        lLoad(frame, 0u)
    }

}

class LLOAD_1: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        lLoad(frame, 1u)
    }

}
class LLOAD_2: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        lLoad(frame, 2u)
    }

}
class LLOAD_3: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        lLoad(frame, 3u)
    }

}