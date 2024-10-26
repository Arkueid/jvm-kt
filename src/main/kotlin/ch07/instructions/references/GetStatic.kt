package ch07.instructions.references

import ch07.instructions.base.Index16Instruction
import ch07.instructions.base.initClass
import ch07.rtdata.KvmFrame
import ch07.rtdata.heap.KvmFieldRef

class GET_STATIC : Index16Instruction() {
    override fun execute(frame: KvmFrame) {
        val cp = frame.method.klass.constantPool
        val fieldRef = cp.getConstant(index) as KvmFieldRef
        val field = fieldRef.resolvedField
        val klass = field.klass

        if (!klass.initStarted) {
            frame.revertNextPC()
            initClass(frame.thread, klass)
            return
        }

        if (!field.isStatic) {
            throw RuntimeException("java.lang.IncompatibleClassChangeError")
        }

        val descriptor = field.descriptor
        val slots = field.klass.staticVars
        val slotId = field.slotId
        val stack = frame.operandStack

        when (descriptor[0]) {
            'Z', 'B', 'C', 'S', 'I' -> stack.pushInt(slots.getInt(slotId))
            'F' -> stack.pushFloat(slots.getFloat(slotId))
            'J' -> stack.pushLong(slots.getLong(slotId))
            'D' -> stack.pushDouble(slots.getDouble(slotId))
            // 字符串或者数组
            'L', '[' -> stack.pushRef(slots.getRef(slotId))
        }
    }

}