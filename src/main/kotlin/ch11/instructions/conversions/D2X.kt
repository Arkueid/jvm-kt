package ch11.instructions.conversions

import ch11.instructions.base.NoOperandsInstruction
import ch11.rtdata.KvmFrame


class D2I: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val double = popDouble()
        pushInt(double.toInt())
    }

}

class D2F: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val double = popDouble()
        pushFloat(double.toFloat())
    }

}

class D2L: NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val double = popDouble()
        pushLong(double.toLong())
    }

}