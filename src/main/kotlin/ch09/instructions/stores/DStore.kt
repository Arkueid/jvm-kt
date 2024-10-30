package ch09.instructions.stores

import ch09.rtdata.KvmFrame
import ch09.instructions.base.Index8Instruction
import ch09.instructions.base.NoOperandsInstruction

private fun dStore(frame: KvmFrame, index: UInt) {
    val value = frame.operandStack.popDouble()
    frame.localVars.setDouble(index, value)
}

class DSTORE : Index8Instruction() {
    override fun execute(frame: KvmFrame) {
        dStore(frame, index.toUInt())
    }
}

class DSTORE_0 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        dStore(frame, 0u)
    }
}

class DSTORE_1 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        dStore(frame, 1u)
    }
}

class DSTORE_2 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        dStore(frame, 2u)
    }
}

class DSTORE_3 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        dStore(frame, 3u)
    }
}