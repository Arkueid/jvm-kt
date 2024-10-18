package ch05.rtdata

class KvmFrame(
    maxLocals: UInt,
    maxStack: UInt,
) {
    val localVars = KvmLocalVars(maxLocals)
    val operandStack = KvmOperandStack(maxStack)

    var lower: KvmFrame? = null

    lateinit var thread: KvmThread

    fun setNextPC(pc: Int) {
        // TODO
        thread.pc = pc
    }
}
