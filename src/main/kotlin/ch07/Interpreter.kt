package ch07

import ch07.instructions.base.BytecodeReader
import ch07.instructions.base.Instruction
import ch07.rtdata.KvmFrame
import ch07.rtdata.KvmThread
import ch07.rtdata.heap.KvmMethod

private const val showStack = false

fun interpret(method: KvmMethod, logInst: Boolean) {
    val thread = KvmThread()
    val frame = thread.newFrame(method)
    thread.pushFrame(frame)

    try {
        loop(thread, logInst, showStack)
    } catch (e: Exception) {
        handleErr(e, thread)
    }
}

fun loop(thread: KvmThread, logInst: Boolean, showStack: Boolean = false) {
    val reader = BytecodeReader()
    while (true) {
        val frame = thread.currentFrame
        val pc = frame.nextPC
        thread.pc = pc

        reader.reset(frame.method.code, pc)
        val opcode = reader.readUint8()
        val inst = Instruction.create(opcode)
        inst.fetchOperands(reader)
        frame.nextPC = reader.pc

        if (logInst) {
            logInstruction(frame, inst)
        }

        inst.execute(frame)

        if (thread.isStackEmpty) {
            break
        }

        // debug
        if (showStack) {
            showStack(frame)
            println()
        }
    }
}

/**
 * 打印指令
 */
fun logInstruction(frame: KvmFrame, inst: Instruction) {
    val method = frame.method
    val className = method.klass.name
    val methodName = method.name
    println(String.format("${className}.$methodName() %2d ${inst.javaClass.simpleName}", frame.thread.pc))
}

/**
 * 处理执行异常
 */
fun handleErr(e: Exception, thread: KvmThread) {
    showFrames(thread)
    throw RuntimeException(e)
}

/**
 * 打印调用栈
 */
fun showFrames(thread: KvmThread) {
    while (!thread.isStackEmpty) {
        showFrame(thread.popFrame())
    }
}

/**
 * 打印单个栈帧
 */
fun showFrame(frame: KvmFrame) {
    val method = frame.method
    val className = method.klass.name
    println(">> pc: ${frame.nextPC} $className.${method.name}${method.descriptor}")
}

/**
 * 执行过程中显示栈内容
 */
fun showStack(frame: KvmFrame) {
    val localVarsString = frame.localVars.slots.joinToString(
        prefix = "[",
        postfix = "]",
        separator = ", "
    ) { "(num=${it.num}, ref=${it.ref})" }
    println("Local Vars: $localVarsString")
    val operandStackString = frame.operandStack.slots.joinToString(
        prefix = "[",
        postfix = "]",
        separator = ", "
    ) { "(num=${it.num}, ref=${it.ref})" }
    println("Operand Stack: $operandStackString")
}
