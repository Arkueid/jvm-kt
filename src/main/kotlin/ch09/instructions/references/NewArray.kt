package ch09.instructions.references

import ch09.instructions.base.BytecodeReader
import ch09.instructions.base.Instruction
import ch09.rtdata.KvmFrame
import ch09.rtdata.heap.KvmClass
import ch09.rtdata.heap.KvmClassLoader
import ch09.rtdata.heap.newArray

object AType {
    const val AL_BOOLEAN: UByte = 4u
    const val AL_CHAR: UByte = 5u
    const val AL_FLOAT: UByte = 6u
    const val AL_DOUBLE: UByte = 7u
    const val AL_BYTE: UByte = 8u
    const val AL_SHORT: UByte = 9u
    const val AL_INT: UByte = 10u
    const val AL_LONG: UByte = 11u
}

class NEW_ARRAY : Instruction {
    private var atype: UByte = 0u

    override fun fetchOperands(reader: BytecodeReader) {
        atype = reader.readUint8()
    }

    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val count = stack.popInt()
        if (count < 0) {
            throw RuntimeException("java.lang.NegativeArraySizeException")
        }

        val classLoader = frame.method.klass.loader
        val arrClass = getPrimitiveArrayClass(classLoader, atype)
        val arr = arrClass.newArray(count)
        stack.pushRef(arr)
    }

    private fun getPrimitiveArrayClass(loader: KvmClassLoader, b: UByte): KvmClass {
        return when (b) {
            AType.AL_BOOLEAN -> loader.loadClass("[Z")
            AType.AL_CHAR -> loader.loadClass("[C")
            AType.AL_FLOAT -> loader.loadClass("[F")
            AType.AL_DOUBLE -> loader.loadClass("[D")
            AType.AL_BYTE -> loader.loadClass("[B")
            AType.AL_SHORT -> loader.loadClass("[S")
            AType.AL_INT -> loader.loadClass("[I")
            AType.AL_LONG -> loader.loadClass("[J")
            else -> throw RuntimeException("Invalid atype!")
        }
    }

}