package ch09.instructions.stack

import ch09.rtdata.KvmFrame
import ch09.instructions.base.NoOperandsInstruction

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