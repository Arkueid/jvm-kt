package ch08.instructions.control

import ch08.instructions.base.BranchInstruction
import ch08.instructions.base.branch
import ch08.rtdata.KvmFrame

/**
 * 无条件跳转语句
 */
class GOTO : BranchInstruction(){
    override fun execute(frame: KvmFrame) {
        branch(frame, offset)
    }

}

