package ch10.instructions.references

import ch10.rtdata.heap.KvmClass
import ch10.instructions.base.Index16Instruction
import ch10.rtdata.KvmFrame
import ch10.rtdata.heap.KvmClassRef
import ch10.rtdata.heap.isInstanceOf

class CHECK_CAST : Index16Instruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val ref = stack.popRef()
        stack.pushRef(ref) // 不改变栈
        if (ref == null) {
            return
        }

        val cp = frame.method.klass.constantPool
        val klassRef = cp.getConstant(index) as KvmClassRef
        val klass = klassRef.resolvedClass
        if (!ref.isInstanceOf(klass)) {
            throw RuntimeException("java.lang.ClassCastException")
        }
    }
}