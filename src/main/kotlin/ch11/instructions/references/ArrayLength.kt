package ch11.instructions.references

import ch11.instructions.base.NoOperandsInstruction
import ch11.rtdata.KvmFrame
import ch11.rtdata.heap.arrayLength

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