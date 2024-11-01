package ch11.instructions.references

import ch11.instructions.base.Index16Instruction
import ch11.rtdata.KvmFrame
import ch11.rtdata.heap.KvmFieldRef

/**
 * 获取实例字段的值
 */
class GET_FIELD : Index16Instruction() {
    override fun execute(frame: KvmFrame) {
        val cp = frame.method.klass.constantPool
        val fieldRef = cp.getConstant(index) as KvmFieldRef
        val field = fieldRef.resolvedField

        if (field.isStatic) {
            throw RuntimeException("java.lang.IncompatibleClassChange")
        }

        val stack = frame.operandStack
        val ref = stack.popRef()
        if (ref == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }

        val descriptor = field.descriptor
        val slotId = field.slotId
        val slots = ref.fields

        when (descriptor[0]) {
            'Z', 'B', 'C', 'S', 'I' -> stack.pushInt(slots.getInt(slotId))
            'F' -> stack.pushFloat(slots.getFloat(slotId))
            'J' -> stack.pushLong(slots.getLong(slotId))
            'D' -> stack.pushDouble(slots.getDouble(slotId))
            'L', '[' -> stack.pushRef(slots.getRef(slotId))
        }

    }
}