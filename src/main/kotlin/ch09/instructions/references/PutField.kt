package ch09.instructions.references

import ch09.instructions.base.Index16Instruction
import ch09.rtdata.KvmFrame
import ch09.rtdata.heap.KvmFieldRef

class PUT_FIELD : Index16Instruction() {
    override fun execute(frame: KvmFrame) {
        val currentMethod = frame.method
        val currentClass = currentMethod.klass
        val cp = currentClass.constantPool
        val fieldRef = cp.getConstant(index) as KvmFieldRef
        val field = fieldRef.resolvedField

        if (field.isStatic) { // 字段必须是实例字段，不能为静态
            throw RuntimeException("java.lang.IncompatibleClassChangeError")
        }

        if (field.isFinal) { // 同一个类的初始化函数才能为 final 字段赋值
            if (currentClass != field.klass || field.name != "<init>") {
                throw RuntimeException("java.lang.IllegalAccessError")
            }
        }

        val descriptor = field.descriptor
        val slotId = field.slotId
        val stack = frame.operandStack

        when (descriptor[0]) {
            'Z', 'B', 'C', 'S', 'I' -> {
                val value = stack.popInt()
                val ref = stack.popRef()
                if (ref == null) {
                    throw RuntimeException("java.lang.NullPointerException")
                }
                ref.fields.setInt(slotId, value)
            }

            'F' -> {
                val value = stack.popFloat()
                val ref = stack.popRef()
                if (ref == null) {
                    throw RuntimeException("java.lang.NullPointerException")
                }
                ref.fields.setFloat(slotId, value)
            }

            'J' -> {
                val value = stack.popLong()
                val ref = stack.popRef()
                if (ref == null) {
                    throw RuntimeException("java.lang.NullPointerException")
                }
                ref.fields.setLong(slotId, value)
            }

            'D' -> {
                val value = stack.popDouble()
                val ref = stack.popRef()
                if (ref == null) {
                    throw RuntimeException("java.lang.NullPointerException")
                }
                ref.fields.setDouble(slotId, value)
            }

            'L', '[' -> {
                val value = stack.popRef()
                val ref = stack.popRef()
                if (ref == null) {
                    throw RuntimeException("java.lang.NullPointerException")
                }
                ref.fields.setRef(slotId, value)
            }

            else -> {}
        }
    }
}