package ch06

import ch06.instructions.base.BytecodeReader
import ch06.instructions.base.Instruction
import ch06.rtdata.KvmFrame
import ch06.rtdata.KvmThread
import ch06.rtdata.heap.KvmMethod

@OptIn(ExperimentalUnsignedTypes::class)
fun interpret(method: KvmMethod) {
    val thread = KvmThread()
    val frame = thread.newFrame(method)
    thread.pushFrame(frame)

    try {
        loop(thread, method.code)
    } catch (e: Exception) {
        handleErr(e, frame)
    }
}

fun loop(thread: KvmThread, byteCode: ByteArray) {
    val frame = thread.popFrame()
    val reader = BytecodeReader()
    while (true) {
        val pc = frame.nextPC
        thread.pc = pc
        reader.reset(byteCode, pc)

        val opcode = reader.readUint8()
        val inst = Instruction.create(opcode)
        inst.fetchOperands(reader)

        frame.nextPC = reader.pc

        println(String.format("pc: %2d inst: ${inst.javaClass.simpleName}", pc))
        inst.execute(frame)
        // debug
        showFrame(frame)
        println()
    }
}

fun handleErr(e: Exception, frame: KvmFrame) {
    showFrame(frame)
    throw RuntimeException(e)
}

fun showFrame(frame: KvmFrame) {
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
