package ch08.rtdata

import ch08.rtdata.heap.KvmMethod

class KvmFrame(
    val thread: KvmThread,
    val method: KvmMethod,
    maxLocals: UInt,
    maxStack: UInt,
) {
    fun revertNextPC() {
        _nextPC = thread.pc
    }

    val localVars = KvmLocalVars(maxLocals)
    val operandStack = KvmOperandStack(maxStack)

    var lower: KvmFrame? = null

    private var _nextPC: Int = 0
    var nextPC
        get() = _nextPC
        set(value) {
            _nextPC = value
        }
}
