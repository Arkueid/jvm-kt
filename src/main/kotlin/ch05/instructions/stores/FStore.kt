package ch05.instructions.stores

import ch05.rtdata.KvmFrame
import ch05.instructions.base.Index8Instruction
import ch05.instructions.base.NoOperandsInstruction

private fun fStore(frame: KvmFrame, index: UInt) {
    val value = frame.operandStack.popFloat()
    frame.localVars.setFloat(index, value)
}

class FSTORE : Index8Instruction() {
    override fun execute(frame: KvmFrame) {
        fStore(frame, index)
    }
}

class FSTORE_0 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        fStore(frame, 0u)
    }
}

class FSTORE_1 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        fStore(frame, 1u)
    }
}

class FSTORE_2 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        fStore(frame, 2u)
    }
}

class FSTORE_3 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        fStore(frame, 3u)
    }
}