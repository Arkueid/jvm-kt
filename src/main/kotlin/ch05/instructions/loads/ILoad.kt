package ch05.instructions.loads

import ch05.rtdata.KvmFrame
import ch05.instructions.base.Index8Instruction
import ch05.instructions.base.NoOperandsInstruction

private fun iLoad(frame: KvmFrame, index: UInt) {
    val val1 = frame.localVars.getInt(index)
    frame.operandStack.pushInt(val1)
}

class ILOAD : Index8Instruction() {
    override fun execute(frame: KvmFrame) {
        iLoad(frame, index.toUInt())
    }
}

class ILOAD_0 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        iLoad(frame, 0u)
    }
}

class ILOAD_1 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        iLoad(frame, 1u)
    }
}

class ILOAD_2 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        iLoad(frame, 2u)
    }
}

class ILOAD_3 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        iLoad(frame, 3u)
    }
}

