package ch10.instructions.control

import ch10.instructions.base.BranchInstruction
import ch10.instructions.base.branch
import ch10.rtdata.KvmFrame

/**
 * 无条件跳转语句
 */
class GOTO : BranchInstruction(){
    override fun execute(frame: KvmFrame) {
        branch(frame, offset)
    }

}

