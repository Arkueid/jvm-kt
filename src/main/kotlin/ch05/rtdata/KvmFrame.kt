package ch05.rtdata

class KvmFrame(
    val thread: KvmThread,
    maxLocals: UInt,
    maxStack: UInt,
) {
    val localVars = KvmLocalVars(maxLocals)
    val operandStack = KvmOperandStack(maxStack)

    var lower: KvmFrame? = null

    private var _nextPC: Int = 0
    var nextPC get() = _nextPC
        set(value) {
        _nextPC = value
    }
}
