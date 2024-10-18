package ch05.instructions.math

import ch05.instructions.base.BytecodeReader
import ch05.instructions.base.Index8Instruction
import ch05.rtdata.KvmFrame


/**
 * 为局部变量表中的变量增加一个常量值
 */
class IINC: Index8Instruction() {
    private var const: Int = 0

    override fun fetchOperands(reader: BytecodeReader) {
        index = reader.readUint8()
        const = reader.readInt8().toInt()
    }

    override fun execute(frame: KvmFrame) {
        val value = frame.localVars.getInt(index.toUInt())
        frame.localVars.setInt(index.toUInt(), value + const)
    }

}