package ch05.instructions.constants

import ch05.rtdata.KvmFrame
import ch05.instructions.base.NoOperandsInstruction

/**
 * 无操作
 */
class NOP : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        // do nothing
    }
}

