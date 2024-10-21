package ch06.instructions.stack

import ch06.rtdata.KvmFrame
import ch06.instructions.base.NoOperandsInstruction

class POP : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        frame.operandStack.popSlot()
    }

}

/**
 * 弹出两个slot，通常为对long/double进行操作
 */
class POP2 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        frame.operandStack.apply {
            popSlot()
            popSlot()
        }
    }

}