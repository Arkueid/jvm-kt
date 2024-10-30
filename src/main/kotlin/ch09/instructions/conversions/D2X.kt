package ch09.instructions.conversions

import ch09.instructions.base.NoOperandsInstruction
import ch09.rtdata.KvmFrame


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