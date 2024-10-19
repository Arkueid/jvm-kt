package ch05.instructions.control

import ch05.instructions.base.BranchInstruction
import ch05.instructions.base.branch
import ch05.rtdata.KvmFrame

/**
 * 无条件跳转语句
 */
class GOTO : BranchInstruction(){
    override fun execute(frame: KvmFrame) {
        branch(frame, offset)
    }

}

