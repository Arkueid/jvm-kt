package ch08.rtdata

import ch08.rtdata.heap.KvmMethod

class KvmThread {
    var stack: KvmStack = KvmStack(1024u)
    private var _pc: Int = 0
    var pc
        get() = _pc
        set(value) {
            _pc = value
        }

    fun pushFrame(frame: KvmFrame) = stack.push(frame)

    fun popFrame(): KvmFrame = stack.pop()

    val currentFrame: KvmFrame get() = stack.top
    val topFrame: KvmFrame get() = stack.top


    fun newFrame(method: KvmMethod): KvmFrame =
        KvmFrame(this, method, method.maxLocals, method.maxStack)

    val isStackEmpty: Boolean get() = stack.isEmpty
}