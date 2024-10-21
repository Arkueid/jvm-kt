package ch06.rtdata

import ch06.rtdata.heap.KvmObject

class KvmLocalVars(
    maxLocals: UInt,
) {
    var slots: Array<KvmSlot>

    init {
        // TODO: 处理 uint -> int
        assert(maxLocals <= Int.MAX_VALUE.toUInt())

        slots = Array(maxLocals.toInt()) { KvmSlot() }
    }

    fun getInt(index: UInt): Int {
        return slots[index.toInt()].num
    }

    fun setInt(index: UInt, value: Int) {
        slots[index.toInt()].num = value
    }

    fun getFloat(index: UInt): Float {
        return Float.fromBits(slots[index.toInt()].num)
    }

    fun setFloat(index: UInt, value: Float) {
        slots[index.toInt()].num = value.toBits()
    }

    fun getLong(index: UInt): Long {
        val long = slots[index.toInt()].num.toLong() and 0xffffffff
        return long or (slots[index.toInt() + 1].num.toLong() shl 32)
    }

    fun setLong(index: UInt, value: Long) {
        slots[index.toInt()].num = value.toInt()
        slots[index.toInt() + 1].num = ((value shr 32) and 0xffffffff).toInt()
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
        return slots[index.toInt()].ref
    }

    fun setRef(index: UInt, value: KvmObject?) {
        slots[index.toInt()].ref = value
    }
}
