package ch10.instructions.extended

import ch10.instructions.base.BranchInstruction
import ch10.instructions.base.BytecodeReader
import ch10.instructions.base.branch
import ch10.rtdata.KvmFrame

class GOTO_W: BranchInstruction() {
    override fun fetchOperands(reader: BytecodeReader) {
        offset = reader.readInt32()
    }

    override fun execute(frame: KvmFrame) {
        branch(frame, offset)
    }

}