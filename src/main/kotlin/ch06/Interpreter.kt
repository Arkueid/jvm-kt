package ch06

import ch06.classfile.MemberInfo
import ch06.instructions.base.BytecodeReader
import ch06.instructions.base.Instruction
import ch06.rtdata.KvmThread

@OptIn(ExperimentalUnsignedTypes::class)
fun interpret(methodInfo: MemberInfo) {
    val codeAttr = methodInfo.codeAttribute
    val maxLocals: UInt = codeAttr!!.maxLocals.toUInt()
    val maxStack: UInt = codeAttr.maxStack.toUInt()
    val byteCode = codeAttr.code

    val thread = KvmThread()
    val frame = thread.newFrame(maxLocals, maxStack)
    thread.pushFrame(frame)

    try {
        loop(thread, byteCode)
    } catch (e: Exception) {
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
        throw RuntimeException(e)
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
    }
}
