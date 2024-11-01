package ch10.instructions.loads

import ch10.instructions.base.Index8Instruction
import ch10.instructions.base.NoOperandsInstruction
import ch10.rtdata.KvmFrame

private fun fLoad(frame: KvmFrame, index: UInt) = frame.operandStack.run {
    pushFloat(frame.localVars.getFloat(index))
}

class FLOAD: Index8Instruction() {
    override fun execute(frame: KvmFrame) {
        fLoad(frame, index)
    }

}

class FLOAD_0: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        fLoad(frame, 0u)
    }

}

class FLOAD_1: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        fLoad(frame, 1u)
    }

}
class FLOAD_2: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        fLoad(frame, 2u)
    }

}
class FLOAD_3: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        fLoad(frame, 3u)
    }

}