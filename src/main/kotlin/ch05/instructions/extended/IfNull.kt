package ch05.instructions.extended

import ch05.instructions.base.BranchInstruction
import ch05.instructions.base.branch
import ch05.rtdata.KvmFrame

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