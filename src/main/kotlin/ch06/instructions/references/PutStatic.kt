package ch06.instructions.references

import ch06.instructions.base.Index16Instruction
import ch06.rtdata.KvmFrame
import ch06.rtdata.heap.KvmFieldRef

class PUT_STATIC : Index16Instruction() {
    override fun execute(frame: KvmFrame) {
        val currentMethod = frame.method
        val currentClass = currentMethod.klass
        val cp = currentClass.constantPool
        val fieldRef = cp.getConstant(index) as KvmFieldRef
        val field = fieldRef.resolvedField
        val klass = field.klass

        if (!field.isStatic) {
            throw RuntimeException("java.lang.IncompatibleClassChangeError")
        }

        if (field.isFinal) {
            // 必须在同一个类并且在初始化函数中才能写 final 修饰的 field
            if (currentClass != klass || currentMethod.name != "<clinit>") { // class init function
                throw RuntimeException("java.lang.IllegalAccessError")
            }
        }

        val descriptor = field.descriptor
        val slotId = field.slotId
        val slots = klass.staticVars
        val stack = frame.operandStack

        when (descriptor[0]) {
            'Z', 'B', 'C', 'S', 'I' -> slots.setInt(slotId, stack.popInt())
            'F' -> slots.setFloat(slotId, stack.popFloat())
            'J' -> slots.setLong(slotId, stack.popLong())
            'D' -> slots.setDouble(slotId, stack.popDouble())
            // 字符串或者数组
            'L', '[' -> slots.setRef(slotId, stack.popRef())
        }
    }
}