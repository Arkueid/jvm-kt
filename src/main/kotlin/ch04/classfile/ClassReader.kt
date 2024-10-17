package ch04.classfile

import kotlin.UShortArray

/**
 * 默认为大端存储
 */
@OptIn(ExperimentalUnsignedTypes::class)
class ClassReader(raw: ByteArray) {
    val data = raw.asUByteArray()

    private var currentPosition: Int = 0

    fun readUint8(): UByte {
        return data[currentPosition++].toUByte()
    }

    fun readUint16(): UShort {
        val b1 = data[currentPosition++].toUInt() and 0xffu
        val b2 = data[currentPosition++].toUInt() and 0xffu
        return ((b1 shl 8) or b2).toUShort()
    }

    fun readUint32(): UInt {
        // 读入的为带符号 byte，需要去除高位符号位
        val b1 = data[currentPosition++].toUInt() and 0xffu
        val b2 = data[currentPosition++].toUInt() and 0xffu
        val b3 = data[currentPosition++].toUInt() and 0xffu
        val b4 = data[currentPosition++].toUInt() and 0xffu

        return (b1 shl 24) or (b2 shl 16) or (b3 shl 8) or b4
    }

//    fun UInt.test() = this.apply { println(String.format("%h", this)) }

    fun readUint64(): ULong {
        var uLong = 0UL
        var shift = 64 - 8
        repeat(8) {
            uLong = uLong or ((data[currentPosition++].toULong() and 0xffuL) shl shift)
            shift -= 8
        }
        return uLong
    }

    /**
     * 读取 uint16 表，表的大小由表头前的 uint16 指出
     */
    @OptIn(ExperimentalUnsignedTypes::class)
    fun readUint16s(): UShortArray {
        val size = readUint16().toInt() // toInt 不会导致最高 16 位全 1
        val uShortArray = UShortArray(size)
        repeat(size) {
            uShortArray[it] = readUint16()
        }
        return uShortArray
    }

    fun readBytes(n: Int): UByteArray {
        return data.sliceArray(currentPosition until currentPosition + n).also { currentPosition += n }
    }
}