package ch09.instructions.loads

import ch09.rtdata.KvmFrame
import ch09.instructions.base.Index8Instruction
import ch09.instructions.base.NoOperandsInstruction

private fun aLoad(frame: KvmFrame, index: UInt) {
    val ref = frame.localVars.getRef(index)
    frame.operandStack.pushRef(ref)
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
