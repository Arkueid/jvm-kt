package ch07.instructions.extended

import ch07.instructions.base.BranchInstruction
import ch07.instructions.base.branch
import ch07.rtdata.KvmFrame

class IFNULL: BranchInstruction() {
    override fun execute(frame: KvmFrame) {
        frame.operandStack.popRef() ?: branch(frame, offset)
    }

}

class IFNONNULL: BranchInstruction() {
    override fun execute(frame: KvmFrame) {
        frame.operandStack.popRef()?.run { branch(frame, offset) }
    }

}