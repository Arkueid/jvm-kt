package ch09.instructions.references

import ch09.instructions.base.Index16Instruction
import ch09.instructions.base.initClass
import ch09.rtdata.KvmFrame
import ch09.rtdata.heap.KvmClassRef

class NEW : Index16Instruction() {
    override fun execute(frame: KvmFrame) {
        val cp = frame.method.klass.constantPool
        val classRef = cp.getConstant(index) as (KvmClassRef)
        val klass = classRef.resolvedClass

        if (!klass.initStarted) {
            frame.revertNextPC()
            initClass(frame.thread, klass)
            return
        }

        if (klass.isInterface || klass.isAbstract) {
            throw RuntimeException("java.lang.InstantiationError")
        }

        val ref = klass.newObject()
        frame.operandStack.pushRef(ref)
    }

}