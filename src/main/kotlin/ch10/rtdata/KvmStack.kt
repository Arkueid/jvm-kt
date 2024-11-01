package ch10.rtdata

class KvmStack(private val maxSize: UInt) {
    private var size: UInt = 0U
    private var _top: KvmFrame? = null

    fun push(frame: KvmFrame) {
        if (size >= maxSize) {
            throw RuntimeException("java.lang.StackOverflowError")
        }

        if (_top != null) {
            frame.lower = _top
        }
        _top = frame
        size++
    }

    fun pop(): KvmFrame {
        val ret = top

        _top = ret.lower
        ret.lower = null
        size--

        return ret
    }

    val top: KvmFrame
        get() = _top ?: throw RuntimeException("jvm stack is empty!")

    val isEmpty: Boolean get() = _top == null

    fun clear() {
        while (!isEmpty) {
            pop()
        }
    }

    val frames: List<KvmFrame>
        get() {
            val list = mutableListOf<KvmFrame>()
            var f = _top
            while (f != null) {
                list.add(f)
                f = f.lower
            }
            return list
        }
}
