package ch06.instructions.extended

import ch06.instructions.base.BranchInstruction
import ch06.instructions.base.BytecodeReader
import ch06.instructions.base.branch
import ch06.rtdata.KvmFrame

class GOTO_W: BranchInstruction() {
    override fun fetchOperands(reader: BytecodeReader) {
        offset = reader.readInt32()
    }

    override fun execute(frame: KvmFrame) {
        branch(frame, offset)
    }

}