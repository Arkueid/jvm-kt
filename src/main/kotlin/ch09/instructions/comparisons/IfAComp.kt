package ch09.instructions.comparisons

import ch09.instructions.base.BranchInstruction
import ch09.instructions.base.branch
import ch09.rtdata.KvmFrame

/**
 * 比较两个引用是否相同
 */


class IF_ACMPEQ : BranchInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val ref2 = popRef()
        val ref1 = popRef()
        if (ref1 == ref2) {
            branch(frame, offset)
        }
    }

}

class IF_ACMPNE : BranchInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val ref2 = popRef()
        val ref1 = popRef()
        if (ref1 != ref2) {
            branch(frame, offset)
        }
    }

}