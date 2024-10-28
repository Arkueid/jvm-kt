package ch08.instructions.extended

import ch08.instructions.base.BranchInstruction
import ch08.instructions.base.branch
import ch08.rtdata.KvmFrame

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