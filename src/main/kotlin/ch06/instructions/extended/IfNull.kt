package ch06.instructions.extended

import ch06.instructions.base.BranchInstruction
import ch06.instructions.base.branch
import ch06.rtdata.KvmFrame

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