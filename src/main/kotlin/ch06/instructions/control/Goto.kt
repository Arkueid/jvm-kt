package ch06.instructions.control

import ch06.instructions.base.BranchInstruction
import ch06.instructions.base.branch
import ch06.rtdata.KvmFrame

/**
 * 无条件跳转语句
 */
class GOTO : BranchInstruction(){
    override fun execute(frame: KvmFrame) {
        branch(frame, offset)
    }

}

