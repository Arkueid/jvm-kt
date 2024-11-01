package ch10.instructions.references

import ch10.instructions.base.NoOperandsInstruction
import ch10.rtdata.KvmFrame
import ch10.rtdata.heap.arrayLength

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