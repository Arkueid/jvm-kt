package ch10.instructions.stores

import ch10.rtdata.KvmFrame
import ch10.instructions.base.Index8Instruction
import ch10.instructions.base.NoOperandsInstruction

private fun lStore(frame: KvmFrame, index: UInt) {
    val value = frame.operandStack.popLong()
    frame.localVars.setLong(index, value)
}

class LSTORE : Index8Instruction() {
    override fun execute(frame: KvmFrame) {
        lStore(frame, index)
    }
}

class LSTORE_0 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        lStore(frame, 0u)
    }
}

class LSTORE_1 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        lStore(frame, 1u)
    }
}

class LSTORE_2 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        lStore(frame, 2u)
    }
}

class LSTORE_3 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        lStore(frame, 3u)
    }
}