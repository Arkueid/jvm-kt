package ch11.instructions.extended

import ch11.instructions.base.BranchInstruction
import ch11.instructions.base.BytecodeReader
import ch11.instructions.base.branch
import ch11.rtdata.KvmFrame

class GOTO_W: BranchInstruction() {
    override fun fetchOperands(reader: BytecodeReader) {
        offset = reader.readInt32()
    }

    override fun execute(frame: KvmFrame) {
        branch(frame, offset)
    }

}