package ch08.instructions.references

import ch08.rtdata.heap.KvmClass
import ch08.instructions.base.Index16Instruction
import ch08.rtdata.KvmFrame
import ch08.rtdata.heap.KvmClassRef
import ch08.rtdata.heap.isInstanceOf

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