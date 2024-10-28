package ch08.instructions.constants

import ch08.rtdata.KvmFrame
import ch08.instructions.base.NoOperandsInstruction

/**
 * 无操作
 */
class NOP : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        // do nothing
    }
}

