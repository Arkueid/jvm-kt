package ch07.instructions.control

import ch07.instructions.base.BranchInstruction
import ch07.instructions.base.branch
import ch07.rtdata.KvmFrame

/**
 * 无条件跳转语句
 */
class GOTO : BranchInstruction(){
    override fun execute(frame: KvmFrame) {
        branch(frame, offset)
    }

}

