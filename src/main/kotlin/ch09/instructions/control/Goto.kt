package ch09.instructions.control

import ch09.instructions.base.BranchInstruction
import ch09.instructions.base.branch
import ch09.rtdata.KvmFrame

/**
 * 无条件跳转语句
 */
class GOTO : BranchInstruction(){
    override fun execute(frame: KvmFrame) {
        branch(frame, offset)
    }

}

