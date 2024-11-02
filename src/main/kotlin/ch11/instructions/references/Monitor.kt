package ch11.instructions.references

import ch11.instructions.base.NoOperandsInstruction
import ch11.rtdata.KvmFrame

class MONITOR_ENTER : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val ref = frame.operandStack.popRef()
        if (ref == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }
    }

}


class MONITOR_EXIT : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val ref = frame.operandStack.popRef()
        if (ref == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }
    }

}