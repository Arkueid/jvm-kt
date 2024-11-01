package ch10.instructions.base

class BytecodeReader {
    private var code: ByteArray = ByteArray(0)

    private var _pc: Int = 0
    val pc: Int get() = _pc

    fun reset(code: ByteArray, pc: Int) {
        this.code = code
        this._pc = pc
    }

    fun readUint8(): UByte {
        return code[_pc++].toUByte()
    }

    fun readInt8(): Byte {
        return readUint8().toByte()
    }

    fun readUint16(): UShort {
        val high = code[_pc++].toUByte().toUInt()  // 符号位不填充高位
        val low = code[_pc++].toUByte().toUInt()
        return (low or (high shl 8)).toUShort()  // 截断
    }

    fun readInt16(): Short {
        return readUint16().toShort()
    }

    fun readInt32(): Int {
        val b1 = readUint8().toUInt()
        val b2 = readUint8().toUInt()
        val b3 = readUint8().toUInt()
        val b4 = readUint8().toUInt()
        return ((b1 shl 24) or (b2 shl 16) or (b3 shl 8) or b4).toInt()
    }

    fun skipPadding() {
        while (_pc % 4 != 0) {
            println("skip")
            readUint8()
        }
    }

    fun readInt32s(count: Int): IntArray {
        return IntArray(count) { readInt32() }
    }

}
