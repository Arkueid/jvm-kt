package ch11.instructions.constants

import ch11.rtdata.KvmFrame
import ch11.instructions.base.NoOperandsInstruction

/**
 * 无操作
 */
class NOP : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        // do nothing
    }
}

