package ch09.rtdata

import ch09.rtdata.heap.KvmObject

class KvmOperandStack(
    maxStack: UInt,
) {
    private var size: Int = 0
    val slots: Array<KvmSlot>

    init {
        // TODO: to fix
        assert(maxStack <= Int.MAX_VALUE.toUInt())

        slots = Array(maxStack.toInt()) { KvmSlot() }
    }

    fun pushInt(value: Int) {
        slots[size].num = value
        size++
    }

    fun popInt(): Int {
        size--
        return slots[size].num
    }

    fun pushFloat(value: Float) {
        slots[size].num = value.toBits()
        size++
    }

    fun popFloat(): Float {
        size--
        return Float.fromBits(slots[size].num)
    }

    fun pushLong(value: Long) {
        slots[size].num = value.toInt()
        size++
        slots[size].num = (value shr 32).toInt()
        size++
    }

    fun popLong(): Long {
        size--
        val long = slots[size].num.toLong() shl 32
        size--
        return long or (slots[size].num.toLong() and 0xffffffff)
    }

    fun pushDouble(value: Double) {
        val bits = value.toBits()
        pushLong(bits)
    }

    fun popDouble(): Double {
        return Double.fromBits(popLong())
    }

    fun pushRef(value: KvmObject?) {
        slots[size].ref = value
        size++
    }

    fun popRef(): KvmObject? {
        size--
        return slots[size].ref.also {
            slots[size].ref = null
        }
    }

    fun pushSlot(slot: KvmSlot) {
        // 不能直接赋值 slots[size] = slot，否则修改另外一个 slot 时，这个位置的值也会改变
        slots[size].ref = slot.ref
        slots[size].num = slot.num
        size++
    }

    fun popSlot(): KvmSlot {
        size--
        return KvmSlot().apply {
            this.ref = slots[size].ref
            this.num = slots[size].num
        }
    }

    fun getRefFromTop(i: Int): KvmObject? {
        return slots[size - i - 1].ref
    }

}
