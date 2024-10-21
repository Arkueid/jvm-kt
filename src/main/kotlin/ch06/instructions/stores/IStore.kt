package ch06.instructions.stores

import ch06.rtdata.KvmFrame
import ch06.instructions.base.Index8Instruction
import ch06.instructions.base.NoOperandsInstruction

private fun iStore(frame: KvmFrame, index: UInt) {
    val value = frame.operandStack.popInt()
    frame.localVars.setInt(index, value)
}

class ISTORE : Index8Instruction() {
    override fun execute(frame: KvmFrame) {
        iStore(frame, index)
    }
}

class ISTORE_0 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        iStore(frame, 0u)
    }
}

class ISTORE_1 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        iStore(frame, 1u)
    }
}

class ISTORE_2 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        iStore(frame, 2u)
    }
}

class ISTORE_3 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        iStore(frame, 3u)
    }
}