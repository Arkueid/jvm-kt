package ch08.instructions.loads

import ch08.instructions.base.Index8Instruction
import ch08.instructions.base.NoOperandsInstruction
import ch08.rtdata.KvmFrame

private fun dLoad(frame: KvmFrame, index: UInt) = frame.operandStack.run {
    pushDouble(frame.localVars.getDouble(index))
}

class DLOAD: Index8Instruction() {
    override fun execute(frame: KvmFrame) {
        dLoad(frame, index)
    }

}

class DLOAD_0: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        dLoad(frame, 0u)
    }

}

class DLOAD_1: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        dLoad(frame, 1u)
    }

}
class DLOAD_2: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        dLoad(frame, 2u)
    }

}
class DLOAD_3: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        dLoad(frame, 3u)
    }

}