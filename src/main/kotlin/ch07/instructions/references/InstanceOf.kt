package ch07.instructions.references

import ch07.instructions.base.Index16Instruction
import ch07.rtdata.KvmFrame
import ch07.rtdata.heap.KvmClassRef

/**
 * ref instanceof klass
 * ref: 操作数栈中给出
 * klass: 在编译时作为指令的操作数（常量池中的索引->classRef->class）
 */
class INSTANCE_OF : Index16Instruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val ref = stack.popRef()
        if (ref == null) {
            stack.pushInt(0)  // false
            return
        }

        val cp = frame.method.klass.constantPool
        val klassRef = cp.getConstant(index) as KvmClassRef
        val klass = klassRef.resolvedClass
        if (ref.isInstanceOf(klass)) {
            stack.pushInt(1)
        } else {
            stack.pushInt(0)
        }
    }
}