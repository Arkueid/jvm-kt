package ch06.instructions.constants

import ch06.rtdata.KvmFrame
import ch06.instructions.base.NoOperandsInstruction

/**
 * 无操作
 */
class NOP : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        // do nothing
    }
}

