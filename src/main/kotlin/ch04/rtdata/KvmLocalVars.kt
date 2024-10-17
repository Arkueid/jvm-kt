package ch04.rtdata

class KvmLocalVars(
    maxLocals: UInt,
) {
    var data: Array<KvmSlot>

    init {
        // TODO: 处理 uint -> int
        assert(maxLocals <= Int.MAX_VALUE.toUInt())

        data = Array(maxLocals.toInt()) { KvmSlot() }
    }

    fun getInt(index: UInt): Int {
        return data[index.toInt()].num
    }

    fun setInt(index: UInt, value: Int) {
        data[index.toInt()].num = value
    }

    fun getFloat(index: UInt): Float {
        return Float.fromBits(data[index.toInt()].num)
    }

    fun setFloat(index: UInt, value: Float) {
        data[index.toInt()].num = value.toBits()
    }

    fun getLong(index: UInt): Long {
        val long = data[index.toInt()].num.toLong() and 0xffffffff
        return long or (data[index.toInt() + 1].num.toLong() shl 32)
    }

    fun setLong(index: UInt, value: Long) {
        data[index.toInt()].num = value.toInt()
        data[index.toInt() + 1].num = ((value shr 32) and 0xffffffff).toInt()
    }

    fun getDouble(index: UInt): Double {
        val bits = getLong(index)
        return Double.fromBits(bits)
    }

    fun setDouble(index: UInt, value: Double) {
        val bits = value.toBits()
        setLong(index, bits)
    }

    fun getRef(index: UInt): KvmObject? {
        return data[index.toInt()].ref
    }

    fun setRef(index: UInt, value: KvmObject?) {
        data[index.toInt()].ref = value
    }
}
