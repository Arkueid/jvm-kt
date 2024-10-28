package ch08.instructions.math

import ch08.instructions.base.NoOperandsInstruction
import ch08.rtdata.KvmFrame

class DSUB : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val v2 = popDouble()
        val v1 = popDouble()
        pushDouble(v1 - v2)
    }

}

class FSUB : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val v2 = popFloat()
        val v1 = popFloat()
        pushFloat(v1 - v2)
    }

}

class ISUB : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val v2 = popInt()
        val v1 = popInt()
        pushInt(v1 - v2)
    }

}

class LSUB : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val v2 = popLong()
        val v1 = popLong()
        pushLong(v1 - v2)
    }

}