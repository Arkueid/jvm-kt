package ch06.instructions.references

import ch06.instructions.base.Index16Instruction
import ch06.rtdata.KvmFrame
import ch06.rtdata.heap.KvmMethodRef
import java.lang.RuntimeException

class INVOKE_VIRTUAL : Index16Instruction() {
    override fun execute(frame: KvmFrame) {
        val cp = frame.method.klass.constantPool
        val methodRef = cp.getConstant(index) as KvmMethodRef
        if (methodRef.name == "println") {
            val stack = frame.operandStack
            when (methodRef.descriptor) {
                "(Z)V" -> println("Boolean: ${stack.popInt() != 0}")
                "(C)V" -> println("Char: ${stack.popInt().toChar()}")
                "(B)V" -> println("Byte: ${stack.popInt().toByte()}")
                "(S)V" -> println("Short: ${stack.popInt()}")
                "(I)V" -> println("Int: ${stack.popInt()}")
                "(J)V" -> println("Long: ${stack.popLong()}")
                "(F)V" -> println("Float: ${stack.popFloat()}")
                "(D)V" -> println("Double: ${stack.popDouble()}")
                else -> throw RuntimeException("println: ${methodRef.descriptor}")
            }
            stack.popRef()
        }
    }
}