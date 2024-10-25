package ch07.instructions.extended

import ch07.instructions.base.BranchInstruction
import ch07.instructions.base.BytecodeReader
import ch07.instructions.base.branch
import ch07.rtdata.KvmFrame

class GOTO_W: BranchInstruction() {
    override fun fetchOperands(reader: BytecodeReader) {
        offset = reader.readInt32()
    }

    override fun execute(frame: KvmFrame) {
        branch(frame, offset)
    }

}