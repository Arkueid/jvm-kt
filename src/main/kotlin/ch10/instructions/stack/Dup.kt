package ch10.instructions.stack

import ch10.instructions.base.NoOperandsInstruction
import ch10.rtdata.KvmFrame

/**
 * 将栈顶元素复制
 *
 * 原栈：底[A, B, C]顶
 * dup后：[A, B, C, *C]
 */
class DUP : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val slot = popSlot()
        pushSlot(slot)
        pushSlot(slot)
    }

}

/**
 * 将栈顶元素的栈顶的元素复制并插入到(栈顶-1)的位置
 * [A, B, C]
 * dup_x1: [A, *C, B, C]
 */
class DUP_X1 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val c = popSlot()
        val b = popSlot()
        pushSlot(c)
        pushSlot(b)
        pushSlot(c)
    }
}

/**
 * 将栈顶元素的栈顶的元素复制并插入到(栈顶-2)的位置
 * [A, B, C]
 * dup_x2: [*C, A, B, C]
 */
class DUP_X2 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val c = popSlot() // x2
        val b = popSlot() // x1
        val a = popSlot() //
        pushSlot(c) // x2
        pushSlot(a) // x
        pushSlot(b)
        pushSlot(c)
    }
}

/**
 * 将栈顶元素的栈顶的两个元素复制并压入栈中
 * [A, B, C]
 * dup2: [A, B, C, *B, *C]
 */
class DUP2 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val c = popSlot()
        val b = popSlot()
        pushSlot(b)
        pushSlot(c)
        pushSlot(b)
        pushSlot(c)
    }
}

/**
 * 将栈顶元素的栈顶的两个元素复制并插入到(栈顶-1)的位置
 * [A, B, C, D]
 * dup2_x1: [A, *C, *D, B, C, D]
 */
class DUP2_X1 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val d = popSlot()
        val c = popSlot()
        val b = popSlot()
        pushSlot(c)
        pushSlot(d)
        pushSlot(b)
        pushSlot(c)
        pushSlot(d)
    }
}

/**
 * 将栈顶元素的栈顶的两个元素复制并插入到(栈顶-2)的位置
 * [A, B, C, D, E]
 * dup2_x2: [A, *D, *E, B, C, D, E]
 */
class DUP2_X2 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = frame.operandStack.run {
        val e = popSlot()
        val d = popSlot()
        val c = popSlot()
        val b = popSlot()
        pushSlot(d)
        pushSlot(e)
        pushSlot(b)
        pushSlot(c)
        pushSlot(d)
        pushSlot(e)
    }
}

