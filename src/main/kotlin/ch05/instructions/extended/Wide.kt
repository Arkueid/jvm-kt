package ch05.instructions.extended

import ch05.instructions.base.BytecodeReader
import ch05.instructions.base.Instruction
import ch05.instructions.loads.*
import ch05.instructions.math.IINC
import ch05.instructions.stores.*
import ch05.rtdata.KvmFrame

class WIDE: Instruction {

    lateinit var modifiedInstruction: Instruction

    override fun fetchOperands(reader: BytecodeReader) {
        val opcode = reader.readUint8().toInt() and 0xFF
        when(opcode) {
            0x15 -> { // iload
                val inst = ILOAD()
                inst.index = reader.readInt16().toUInt()
                modifiedInstruction = inst
            }
            0x16 -> { // lload
                val inst = LLOAD()
                inst.index = reader.readInt16().toUInt()
                modifiedInstruction = inst
            }
            0x17 -> { // fload
                val inst = FLOAD()
                inst.index = reader.readInt16().toUInt()
                modifiedInstruction = inst
            }
            0x18 -> { // dload
                val inst = DLOAD()
                inst.index = reader.readInt16().toUInt()
                modifiedInstruction = inst
            }
            0x19 -> { // aload
                val inst = ALOAD()
                inst.index = reader.readInt16().toUInt()
                modifiedInstruction = inst
            }
            0x36 -> { // istore
                val inst = ISTORE()
                inst.index = reader.readInt16().toUInt()
                modifiedInstruction = inst
            }
            0x37 -> { // lstore
                val inst = LSTORE()
                inst.index = reader.readInt16().toUInt()
                modifiedInstruction = inst
            }
            0x38 -> { // fstore
                val inst = FSTORE()
                inst.index = reader.readInt16().toUInt()
                modifiedInstruction = inst
            }
            0x39 -> { // dstore
                val inst = DSTORE()
                inst.index = reader.readInt16().toUInt()
                modifiedInstruction = inst
            }
            0x3a -> { // astore
                val inst = ASTORE()
                inst.index = reader.readInt16().toUInt()
                modifiedInstruction = inst
            }
            0x84 -> { // iinc
                val inst = IINC()
                inst.index = reader.readInt16().toUInt()
                inst.const = reader.readInt16().toInt()
                modifiedInstruction = inst
            }
            0xa9 -> // ret
                throw RuntimeException("no handler for opcode: 0xa9")
        }
    }

    override fun execute(frame: KvmFrame) {
        modifiedInstruction.execute(frame)
    }

}