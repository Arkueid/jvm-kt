package ch05.rtdata

class KvmThread(private val stack: KvmStack) {
    private var _pc: Int = 0
    var pc
        get() = _pc
        set(value) {
            _pc = value
        }

    fun pushFrame(frame: KvmFrame) = stack.push(frame)

    fun popFrame(): KvmFrame = stack.pop()

    val currentFrame: KvmFrame get() = stack.top
}