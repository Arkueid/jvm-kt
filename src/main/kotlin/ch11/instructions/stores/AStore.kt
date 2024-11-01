package ch11.instructions.stores

import ch11.instructions.base.Index8Instruction
import ch11.instructions.base.NoOperandsInstruction
import ch11.rtdata.KvmFrame

private fun aStore(frame: KvmFrame, index: UInt) {
    frame.localVars.setRef(index, frame.operandStack.popRef())
}

class ASTORE: Index8Instruction() {
    override fun execute(frame: KvmFrame) {
        aStore(frame, index)
    }

}

class ASTORE_0 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        aStore(frame, 0u)
    }
}

class ASTORE_1 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        aStore(frame, 1u)
    }
}

class ASTORE_2 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        aStore(frame, 2u)
    }
}

class ASTORE_3 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        aStore(frame, 3u)
    }
}