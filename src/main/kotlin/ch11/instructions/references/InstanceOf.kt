package ch11.instructions.references

import ch11.instructions.base.Index16Instruction
import ch11.rtdata.KvmFrame
import ch11.rtdata.heap.KvmClassRef
import ch11.rtdata.heap.isInstanceOf

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