package ch07.instructions.comparisons

import ch07.instructions.base.BranchInstruction
import ch07.instructions.base.branch
import ch07.rtdata.KvmFrame

private fun cmp(frame: KvmFrame, rule: (v1: Int, v2: Int)-> Boolean, handle: ()-> Unit) = frame.operandStack.run {
    val v2 = popInt()
    val v1 = popInt()
    if (rule(v1, v2)) {
        handle()
    }
}

class IF_ICMPEQ : BranchInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        cmp(frame, {v1, v2 -> v1 == v2}) {
            branch(frame, offset)
        }
    }

}

class IF_ICMPNE : BranchInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        cmp(frame, {v1, v2 -> v1 != v2}) {
            branch(frame, offset)
        }
    }

}

class IF_ICMPLE : BranchInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        cmp(frame, {v1, v2 -> v1 <= v2}) {
            branch(frame, offset)
        }
    }

}

class IF_ICMPGE : BranchInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        cmp(frame, {v1, v2 -> v1 >= v2}) {
            branch(frame, offset)
        }
    }

}

class IF_ICMPLT : BranchInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        cmp(frame, {v1, v2 -> v1 < v2}) {
            branch(frame, offset)
        }
    }

}

class IF_ICMPGT : BranchInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        cmp(frame, {v1, v2 -> v1 > v2}) {
            branch(frame, offset)
        }
    }

}