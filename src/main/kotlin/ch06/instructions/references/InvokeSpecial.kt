package ch06.instructions.references

import ch06.instructions.base.Index16Instruction
import ch06.rtdata.KvmFrame

class INVOKE_SPECIAL : Index16Instruction() {
    override fun execute(frame: KvmFrame) {
        frame.operandStack.popRef()
    }
}