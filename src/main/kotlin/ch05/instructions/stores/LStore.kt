package ch05.instructions.stores

import ch04.rtdata.KvmFrame
import ch05.instructions.base.Index8Instruction
import ch05.instructions.base.NoOperandsInstruction

private fun lStore(frame: KvmFrame, index: UInt) {
    val value = frame.operandStack.popLong()
    frame.localVars.setLong(index, value)
}

class LStore : Index8Instruction() {
    override fun execute(frame: KvmFrame) {
        lStore(frame, index.toUInt())
    }
}

class LStore_0 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        lStore(frame, 0u)
    }
}

class LStore_1 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        lStore(frame, 1u)
    }
}

class LStore_2 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        lStore(frame, 2u)
    }
}

class LStore_3 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        lStore(frame, 3u)
    }
}