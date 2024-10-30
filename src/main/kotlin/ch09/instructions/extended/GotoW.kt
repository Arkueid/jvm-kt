package ch09.instructions.extended

import ch09.instructions.base.BranchInstruction
import ch09.instructions.base.BytecodeReader
import ch09.instructions.base.branch
import ch09.rtdata.KvmFrame

class GOTO_W: BranchInstruction() {
    override fun fetchOperands(reader: BytecodeReader) {
        offset = reader.readInt32()
    }

    override fun execute(frame: KvmFrame) {
        branch(frame, offset)
    }

}