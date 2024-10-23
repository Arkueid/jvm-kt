/**
 *  计算模数
 */

package ch06.instructions.math

import ch06.instructions.base.NoOperandsInstruction
import ch06.rtdata.KvmFrame

class DREM : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val v2 = popDouble()
        val v1 = popDouble()
        pushDouble(v1 % v2)
    }

}

class FREM : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val v2 = popFloat()
        val v1 = popFloat()
        pushFloat(v1 % v2)
    }

}

class IREM : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val v2 = popInt()
        val v1 = popInt()
        pushInt(v1 % v2)
    }

}

class LREM : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val v2 = popLong()
        val v1 = popLong()
        pushLong(v1 % v2)
    }

}