package ch09.instructions.control

import ch09.instructions.base.BranchInstruction
import ch09.instructions.base.BytecodeReader
import ch09.instructions.base.branch
import ch09.rtdata.KvmFrame

class TABLE_SWITCH : BranchInstruction() {
    private var defaultOffset: Int = 0

    // low 和 high 记录取值范围
    private var low: Int = 0
    private var high: Int = 0
    private var jumpOffsets: IntArray = IntArray(0)

    override fun fetchOperands(reader: BytecodeReader) {
        reader.skipPadding()
        defaultOffset = reader.readInt32()
        low = reader.readInt32()
        high = reader.readInt32()
        val jumpOffsetCount = high - low + 1
        jumpOffsets = reader.readInt32s(jumpOffsetCount)
    }

    override fun execute(frame: KvmFrame) {
        val index = frame.operandStack.popInt()
        offset = if (index in low..high) jumpOffsets[index] else defaultOffset
        branch(frame, offset)
    }

}


class LOOKUP_SWITCH : BranchInstruction() {
    private var defaultOffset: Int = 0
    private var npairs: Int = 0
    private var matchOffsets: IntArray = IntArray(0)

    override fun fetchOperands(reader: BytecodeReader) {
        reader.skipPadding()
        defaultOffset = reader.readInt32()
        npairs = reader.readInt32()
        matchOffsets = reader.readInt32s(npairs * 2)
    }

    override fun execute(frame: KvmFrame) {
        val key = frame.operandStack.popInt()

        for (i in 0 until npairs) {
            if (key == matchOffsets[i]) {
                offset = matchOffsets[i+1]
                branch(frame, offset)
                return
            }
        }

        branch(frame, defaultOffset)
    }

}