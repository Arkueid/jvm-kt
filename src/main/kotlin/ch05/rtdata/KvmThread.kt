package ch05.rtdata

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


    fun newFrame(maxLocals: UInt, maxStack: UInt): KvmFrame = KvmFrame(this, maxLocals, maxStack)
}