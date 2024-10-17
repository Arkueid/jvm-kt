package ch04.rtdata

class KvmFrame(
    maxLocals: UInt,
    maxStack: UInt,
) {
    val localVars = KvmLocalVars(maxLocals)
    val operandStack = KvmOperandStack(maxStack)

    var lower: KvmFrame? = null


}
