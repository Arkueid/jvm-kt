package ch11.instructions.references

import ch11.instructions.base.Index16Instruction
import ch11.instructions.base.invokeMethod
import ch11.rtdata.KvmFrame
import ch11.rtdata.KvmOperandStack
import ch11.rtdata.heap.KvmClass
import ch11.rtdata.heap.kvmStrFromJStr
import ch11.rtdata.heap.KvmMethod
import ch11.rtdata.heap.KvmMethodRef
import ch11.rtdata.heap.isArray
import ch11.rtdata.heap.isSubClassOf
import ch11.rtdata.heap.isSuperClassOf
import ch11.rtdata.heap.lookupMethodInClass

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
            throw RuntimeException("java.lang.NullPointerException")
        }

        if (resolvedMethod.isProtected &&
            resolvedMethod.klass.isSuperClassOf(currentClass) &&
            resolvedMethod.klass.packageName != currentClass.packageName &&
            ref.klass != currentClass &&
            !ref.klass.isSubClassOf(currentClass)
        ) {
            if (!ref.klass.isArray || resolvedMethod.name != "clone") {
                throw RuntimeException("java.lang.IllegalAccessError")
            }
        }

        val methodToBeInvoked: KvmMethod? = lookupMethodInClass(ref.klass, methodRef.name, methodRef.descriptor)

        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract) {
            throw RuntimeException("java.lang.AbstractMethodError: ${ref.klass.name}.${methodRef.name}${methodRef.descriptor}")
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
            val ktStr = kvmStrFromJStr(jStr)
            println("$ktStr")
        }

        else -> throw RuntimeException("println: ${descriptor}")
    }
    stack.popRef()
}