package ch05.instructions.base

import ch05.instructions.InstructionFactory
import ch05.instructions.constants.NOP
import ch05.rtdata.KvmFrame

interface Instruction {
    fun fetchOperands(reader: BytecodeReader)
    fun execute(frame: KvmFrame)

    companion object {
        @JvmStatic
        fun create(opcode: UByte): Instruction {
            return InstructionFactory.create(opcode)
        }
    }
}

/**
 * 无操作数指令
 */
abstract class NoOperandsInstruction : Instruction {
    override fun fetchOperands(reader: BytecodeReader) {
        // do nothing
    }
}


/**
 * 跳转指令
 */
abstract class BranchInstruction : Instruction {
    var offset: Int = 0

    override fun fetchOperands(reader: BytecodeReader) {
        offset = reader.readInt16().toInt()

    }

}


/**
 * 加载和存储等涉及局部变量表索引的指令，索引由1字节给出
 */
abstract class Index8Instruction : Instruction {
    var index: UInt = 0u

    override fun fetchOperands(reader: BytecodeReader) {
        index = reader.readUint8().toUInt()
    }

}

/**
 * 访问运行时常量池，其索引由2字节操作数给出
 */
abstract class Index16Instruction : Instruction {
    var index: UInt = 0u

    override fun fetchOperands(reader: BytecodeReader) {
        index = reader.readUint16().toUInt()
    }
}

