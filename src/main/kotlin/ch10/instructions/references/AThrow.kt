package ch10.instructions.references

import ch10.instructions.base.NoOperandsInstruction
import ch10.rtdata.KvmFrame
import ch10.rtdata.KvmThread
import ch10.rtdata.heap.KvmObject
import ch10.rtdata.heap.kvmStrFromJStr
import java.lang.RuntimeException

class ATHROW : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val ex = frame.operandStack.popRef()
        if (ex == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }

        val thread = frame.thread
        if (!findAndGotoExceptionHandler(thread, ex)) {
            handleUncaughtException(thread, ex)
        }
    }

    private fun handleUncaughtException(thread: KvmThread, ex: KvmObject) {
        thread.clearStack()

        val jMsg = ex.getRefVar("detailMessage", "Ljava/lang/String;")
        val ktMsg = kvmStrFromJStr(jMsg)
        println("${ex.klass.javaName}: $ktMsg")

        ex.exceptionInfo.forEach {
            println("\tat ${it.String()}")
        }
    }

}


private fun findAndGotoExceptionHandler(thread: KvmThread, ex: KvmObject): Boolean {
    while (true) {
        val frame = thread.currentFrame
        val pc = frame.nextPC - 1

        val handlerPC = frame.method.findExceptionHandler(ex.klass, pc)
        if (handlerPC > 0) {
            val stack = frame.operandStack
            stack.clear()
            stack.pushRef(ex)
            frame.nextPC = handlerPC
            return true
        }

        thread.popFrame()

        if (thread.isStackEmpty) {
            break
        }
    }
    return false
}
