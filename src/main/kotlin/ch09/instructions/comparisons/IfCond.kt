package ch09.instructions.comparisons

import ch09.instructions.base.BranchInstruction
import ch09.instructions.base.branch
import ch09.rtdata.KvmFrame

/**
 * 栈顶的数和0比较，如果相等则跳转
 */

class IFEQ: BranchInstruction() {
    override fun execute(frame: KvmFrame) {
        val value = frame.operandStack.popInt()
        if (value == 0) {
            branch(frame, offset)
        }
    }
}

class IFNE: BranchInstruction() {
    override fun execute(frame: KvmFrame) {
        val value = frame.operandStack.popInt()
        if (value != 0) {
            branch(frame, offset)
        }
    }
}

class IFLT: BranchInstruction() {
    override fun execute(frame: KvmFrame) {
        val value = frame.operandStack.popInt()
        if (value < 0) {
            branch(frame, offset)
        }
    }
}

class IFGE: BranchInstruction() {
    override fun execute(frame: KvmFrame) {
        val value = frame.operandStack.popInt()
        if (value >= 0) {
            branch(frame, offset)
        }
    }
}

class IFGT: BranchInstruction() {
    override fun execute(frame: KvmFrame) {
        val value = frame.operandStack.popInt()
        if (value > 0) {
            branch(frame, offset)
        }
    }
}

class IFLE: BranchInstruction() {
    override fun execute(frame: KvmFrame) {
        val value = frame.operandStack.popInt()
        if (value <= 0) {
            branch(frame, offset)
        }
    }
}