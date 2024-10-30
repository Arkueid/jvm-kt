package ch09.instructions.control

import ch09.instructions.base.NoOperandsInstruction
import ch09.rtdata.KvmFrame

/**
 * void
 */
class RETURN : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        frame.thread.popFrame()
    }

}

/**
 * obj ref
 */
class ARETURN : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val currentFrame = frame.thread.popFrame()
        val invokerFrame = frame.thread.topFrame

        invokerFrame.operandStack.pushRef(currentFrame.operandStack.popRef())
    }

}

/**
 * double
 */
class DRETURN : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val currentFrame = frame.thread.popFrame()
        val invokerFrame = frame.thread.topFrame

        invokerFrame.operandStack.pushDouble(currentFrame.operandStack.popDouble())
    }

}

/**
 * float
 */
class FRETURN : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val currentFrame = frame.thread.popFrame()
        val invokerFrame = frame.thread.topFrame

        invokerFrame.operandStack.pushFloat(currentFrame.operandStack.popFloat())
    }

}

/**
 * int
 */
class IRETURN : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val currentFrame = frame.thread.popFrame()
        val invokerFrame = frame.thread.topFrame

        invokerFrame.operandStack.pushInt(currentFrame.operandStack.popInt())
    }

}

/**
 * long
 */
class LRETURN : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val currentFrame = frame.thread.popFrame()
        val invokerFrame = frame.thread.topFrame

        invokerFrame.operandStack.pushLong(currentFrame.operandStack.popLong())
    }

}