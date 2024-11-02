package ch11.rtdata

import ch11.rtdata.heap.KvmMethod

open class KvmFrame() {
    private lateinit var _thread: KvmThread
    val thread get() = _thread
    private lateinit var _method: KvmMethod
    val method get() = _method


    private lateinit var _localVars: KvmLocalVars
    val localVars get() = _localVars
    private lateinit var _operandStack: KvmOperandStack
    val operandStack get() = _operandStack

    constructor(
        thread: KvmThread,
        method: KvmMethod,
        maxLocals: UInt,
        maxStack: UInt
    ) : this() {
        _thread = thread
        _method = method
        _localVars = KvmLocalVars(maxLocals)
        _operandStack = KvmOperandStack(maxStack)
    }

    constructor(thread: KvmThread, method: KvmMethod, operandStack: KvmOperandStack) : this() {
        _thread = thread
        _method = method
        _operandStack = operandStack
    }

    fun revertNextPC() {
        _nextPC = thread.pc
    }


    var lower: KvmFrame? = null

    private var _nextPC: Int = 0
    var nextPC
        get() = _nextPC
        set(value) {
            _nextPC = value
        }
}
