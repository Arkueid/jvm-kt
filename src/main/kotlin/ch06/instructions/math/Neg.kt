package ch06.instructions.math

import ch06.instructions.base.NoOperandsInstruction
import ch06.rtdata.KvmFrame

class DNEG : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val value = popDouble()
        pushDouble(-value)
    }

}

class FNEG : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val value = popFloat()
        pushFloat(-value)
    }

}

class INEG : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val value = popInt()
        pushInt(-value)
    }

}

class LNEG : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val value = popLong()
        pushLong(-value)
    }

}