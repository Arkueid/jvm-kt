package ch05.instructions.extended

import ch05.instructions.base.BranchInstruction
import ch05.instructions.base.BytecodeReader
import ch05.instructions.base.branch
import ch05.rtdata.KvmFrame

class GOTO_W: BranchInstruction() {
    override fun fetchOperands(reader: BytecodeReader) {
        offset = reader.readInt32()
    }

    override fun execute(frame: KvmFrame) {
        branch(frame, offset)
    }

}