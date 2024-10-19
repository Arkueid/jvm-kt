package ch05.instructions.stack

import ch05.instructions.base.NoOperandsInstruction
import ch05.rtdata.KvmFrame

/**
 * 交换操作数栈顶的两个元素
 */
class SWAP : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val v0 = popSlot()
        val v1 = popSlot()
        pushSlot(v0)
        pushSlot(v1)
    }
}