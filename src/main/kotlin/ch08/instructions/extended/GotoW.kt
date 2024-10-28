package ch08.instructions.extended

import ch08.instructions.base.BranchInstruction
import ch08.instructions.base.BytecodeReader
import ch08.instructions.base.branch
import ch08.rtdata.KvmFrame

class GOTO_W: BranchInstruction() {
    override fun fetchOperands(reader: BytecodeReader) {
        offset = reader.readInt32()
    }

    override fun execute(frame: KvmFrame) {
        branch(frame, offset)
    }

}