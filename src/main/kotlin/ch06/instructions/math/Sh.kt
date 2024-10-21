/**
 * 移位操作
 */

package ch06.instructions.math

import ch06.instructions.base.NoOperandsInstruction
import ch06.rtdata.KvmFrame

class ISHL : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val v2 = popInt()
        val v1 = popInt()
        val result = v1 shl (v2 and 0x1f)
        pushInt(result)
    }

}

class ISHR : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val v2 = popInt()
        val v1 = popInt()
        val result = v1 shr (v2 and 0x1f)
        pushInt(result)
    }

}

class IUSHR : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val v2 = popInt()
        val v1 = popInt()
        val result = v1 ushr (v2 and 0x1f)
        pushInt(result)
    }

}


class LSHL : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val v2 = popInt()
        val v1 = popLong()
        val result = v1 shl (v2 and 0x3f)
        pushLong(result)
    }

}

class LSHR : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val v2 = popInt()
        val v1 = popLong()
        val result = v1 shr (v2 and 0x3f)
        pushLong(result)
    }

}

class LUSHR : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val v2 = popInt()
        val v1 = popLong()
        val result = v1 ushr (v2 and 0x3f)
        pushLong(result)
    }

}