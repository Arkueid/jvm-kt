package ch06.instructions.constants

import ch06.instructions.base.BytecodeReader
import ch06.instructions.base.Instruction
import ch06.rtdata.KvmFrame

/**
 * bipush
 */
class BIPUSH : Instruction {
    var value: Byte = 0

    override fun fetchOperands(reader: BytecodeReader) {
        value = reader.readInt8()
    }

    override fun execute(frame: KvmFrame) {
        frame.operandStack.pushInt(value.toInt())
    }
}

/**
 * sipush
 */
class SIPUSH : Instruction {
    var value: Short = 0
    override fun fetchOperands(reader: BytecodeReader) {
        value = reader.readInt16()
    }

    override fun execute(frame: KvmFrame) {
        frame.operandStack.pushInt(value.toInt())
    }
}