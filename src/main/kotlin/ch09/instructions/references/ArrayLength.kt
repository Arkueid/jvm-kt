package ch09.instructions.references

import ch09.instructions.base.NoOperandsInstruction
import ch09.rtdata.KvmFrame
import ch09.rtdata.heap.arrayLength

class ARRAY_LENGTH : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val arrRef = stack.popRef()
        if (arrRef == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }

        val arrLen = arrRef.arrayLength
        stack.pushInt(arrLen)
    }

}