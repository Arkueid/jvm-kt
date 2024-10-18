package ch05.rtdata

class KvmOperandStack(
    maxStack: UInt,
) {
    private var size: Int = 0
    private val slots: Array<KvmSlot>

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
        slots[size] = slot
        size++
    }

    fun popSlot(): KvmSlot {
        size--
        return slots[size]
    }

}
