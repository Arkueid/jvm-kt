package ch08.instructions.references

import ch08.instructions.base.NoOperandsInstruction
import ch08.rtdata.KvmFrame
import ch08.rtdata.heap.arrayLength

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