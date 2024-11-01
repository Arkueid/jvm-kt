package ch10.instructions.conversions

import ch10.instructions.base.NoOperandsInstruction
import ch10.rtdata.KvmFrame


class I2B: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val int = popInt()
        pushInt(int.toByte().toInt())
    }

}

// java char 2 字节
class I2C: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val int = popInt()
        pushInt(int.toUShort().toInt())
    }

}

class I2S: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val int = popInt()
        pushInt(int.toShort().toInt())
    }

}


class I2D: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val int = popInt()
        pushDouble(int.toDouble())
    }

}

class I2F: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val int = popInt()
        pushFloat(int.toFloat())
    }

}

class I2L: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val int = popInt()
        pushLong(int.toLong())
    }

}