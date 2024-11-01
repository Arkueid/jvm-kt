package ch11.instructions.control

import ch11.instructions.base.BranchInstruction
import ch11.instructions.base.branch
import ch11.rtdata.KvmFrame

/**
 * 无条件跳转语句
 */
class GOTO : BranchInstruction(){
    override fun execute(frame: KvmFrame) {
        branch(frame, offset)
    }

}

