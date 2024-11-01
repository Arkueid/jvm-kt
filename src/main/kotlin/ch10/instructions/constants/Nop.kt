package ch10.instructions.constants

import ch10.rtdata.KvmFrame
import ch10.instructions.base.NoOperandsInstruction

/**
 * 无操作
 */
class NOP : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        // do nothing
    }
}

