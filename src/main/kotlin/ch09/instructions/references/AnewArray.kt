package ch09.instructions.references

import ch09.instructions.base.Index16Instruction
import ch09.rtdata.KvmFrame
import ch09.rtdata.heap.KvmClassRef
import ch09.rtdata.heap.newArray

class ANEW_ARRAY : Index16Instruction() {
    override fun execute(frame: KvmFrame) {
        val cp = frame.method.klass.constantPool
        val classRef = cp.getConstant(index) as KvmClassRef
        val componentClass = classRef.resolvedClass
        val stack = frame.operandStack
        val count = stack.popInt()
        if (count < 0) {
            throw RuntimeException("java.lang.NegativeArraySizeException")
        }

        val arrClass = componentClass.arrayClass
        val arr = arrClass.newArray(count)
        stack.pushRef(arr)
    }

}