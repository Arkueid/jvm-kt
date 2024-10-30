package ch09.instructions.references

import ch09.instructions.base.Index16Instruction
import ch09.instructions.base.invokeMethod
import ch09.rtdata.KvmFrame
import ch09.rtdata.KvmOperandStack
import ch09.rtdata.heap.kvmJStr2KtStr
import ch09.rtdata.heap.KvmMethod
import ch09.rtdata.heap.KvmMethodRef
import ch09.rtdata.heap.isSubClassOf
import ch09.rtdata.heap.isSuperClassOf
import ch09.rtdata.heap.lookupMethodInClass

class INVOKE_VIRTUAL : Index16Instruction() {
    override fun execute(frame: KvmFrame) {
        val currentClass = frame.method.klass
        val cp = currentClass.constantPool
        val methodRef = cp.getConstant(index) as KvmMethodRef
        val resolvedMethod = methodRef.resolvedMethod
        if (resolvedMethod.isStatic) {
            throw RuntimeException("java.lang.IncompatibleClassChangeError")
        }

        val ref = frame.operandStack.getRefFromTop(resolvedMethod.argSlotCount - 1)
        if (ref == null) {
            // TODO: hack!
            if (methodRef.name == "println") {
                _println(frame.operandStack, methodRef.descriptor)
                return
            }
            throw RuntimeException("java.lang.NullPointerException")
        }

        if (resolvedMethod.isProtected &&
            resolvedMethod.klass.isSuperClassOf(currentClass) &&
            ref.klass != currentClass &&
            !ref.klass.isSubClassOf(currentClass)
        ) {
            throw RuntimeException("java.lang.IllegalAccessError")
        }

        val methodToBeInvoked: KvmMethod? = lookupMethodInClass(ref.klass, methodRef.name, methodRef.descriptor)

        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract) {
            throw RuntimeException("java.lang.AbstractMethodError")
        }

        invokeMethod(frame, methodToBeInvoked)
    }
}


private fun _println(stack: KvmOperandStack, descriptor: String) {
    when (descriptor) {
        "(Z)V" -> println("${stack.popInt() != 0}")
        "(C)V" -> println("${stack.popInt().toChar()}")
        "(B)V" -> println("${stack.popInt().toByte()}")
        "(S)V" -> println("${stack.popInt()}")
        "(I)V" -> println("${stack.popInt()}")
        "(J)V" -> println("${stack.popLong()}")
        "(F)V" -> println("${stack.popFloat()}")
        "(D)V" -> println("${stack.popDouble()}")
        "(Ljava/lang/String;)V" -> {
            val jStr = stack.popRef()
            val ktStr = kvmJStr2KtStr(jStr)
            println("$ktStr")
        }

        else -> throw RuntimeException("println: ${descriptor}")
    }
    stack.popRef()
}