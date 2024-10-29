package ch08.instructions.references

import ch08.instructions.base.BytecodeReader
import ch08.instructions.base.Instruction
import ch08.rtdata.KvmFrame
import ch08.rtdata.KvmOperandStack
import ch08.rtdata.heap.KvmClass
import ch08.rtdata.heap.KvmClassRef
import ch08.rtdata.heap.KvmObject
import ch08.rtdata.heap.componentClass
import ch08.rtdata.heap.newArray
import ch08.rtdata.heap.refs

class MULTI_ANEW_ARRAY : Instruction {
    private var index: UShort = 0u
    private var dimentions: UByte = 0u

    override fun fetchOperands(reader: BytecodeReader) {
        index = reader.readUint16()
        dimentions = reader.readUint8()
    }

    override fun execute(frame: KvmFrame) {
        val cp = frame.method.klass.constantPool
        val classRef = cp.getConstant(index.toUInt()) as KvmClassRef
        val arrClass = classRef.resolvedClass
        val stack = frame.operandStack
        val counts = popAndCheckCounts(stack, dimentions.toInt())
        val arr = newMultiDimensionalArray(0, counts, arrClass)
        stack.pushRef(arr)
    }

    private fun popAndCheckCounts(stack: KvmOperandStack, dimensions: Int): Array<Int> {
        val counts = Array<Int>(dimensions) { 0 }
        for (i in dimensions - 1 downTo 0) {
            counts[i] = stack.popInt()
            if (counts[i] < 0) {
                throw RuntimeException("java.lang.NegativeArraySizeException")
            }
        }
        return counts
    }

    private fun newMultiDimensionalArray(index: Int, counts: Array<Int>, arrClass: KvmClass): KvmObject {
        val count = counts[0]
        val arr = arrClass.newArray(count)

        if (counts.size - 1 > index) {
            val refs = arr.refs
            for (i in 0 until refs.size) {
                refs[i] = newMultiDimensionalArray(index + 1, counts, arrClass.componentClass)
            }
        }

        return arr
    }

}