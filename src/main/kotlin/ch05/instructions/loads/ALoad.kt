package ch05.instructions.loads

import ch05.rtdata.KvmFrame
import ch05.instructions.base.Index8Instruction
import ch05.instructions.base.NoOperandsInstruction

private fun aLoad(frame: KvmFrame, index: UInt) {
    val val1 = frame.localVars.getInt(index)
    frame.operandStack.pushInt(val1)
}

class ALOAD : Index8Instruction() {
    override fun execute(frame: KvmFrame) {
        aLoad(frame, index)
    }
}

class ALOAD_0 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        aLoad(frame, 0u)
    }
}

class ALOAD_1 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        aLoad(frame, 1u)
    }
}

class ALOAD_2 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        aLoad(frame, 2u)
    }
}

class ALOAD_3 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        aLoad(frame, 3u)
    }
}

