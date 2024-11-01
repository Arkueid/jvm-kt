package ch10.instructions.extended

import ch10.instructions.base.BranchInstruction
import ch10.instructions.base.branch
import ch10.rtdata.KvmFrame

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