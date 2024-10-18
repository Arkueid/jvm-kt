package ch05.instructions.constants

import ch05.instructions.base.BytecodeReader
import ch05.instructions.base.Instruction
import ch05.rtdata.KvmFrame

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