package ch06.instructions.math

import ch06.instructions.base.BytecodeReader
import ch06.instructions.base.Index8Instruction
import ch06.rtdata.KvmFrame


/**
 * 为局部变量表中的变量增加一个常量值
 */
class IINC: Index8Instruction() {
    var const: Int = 0

    override fun fetchOperands(reader: BytecodeReader) {
        index = reader.readUint8().toUInt()
        const = reader.readInt8().toInt()
    }

    override fun execute(frame: KvmFrame) {
        val value = frame.localVars.getInt(index.toUInt())
        frame.localVars.setInt(index.toUInt(), value + const)
    }

}