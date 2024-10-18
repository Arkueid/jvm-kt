package ch05.instructions.base

class BytecodeReader {
    private var code: ByteArray = ByteArray(0)

    private var pc: Int = 0

    fun reset(code: ByteArray, pc: Int) {
        this.code = code
        this.pc = pc
    }

    fun readUint8(): UByte {
        return code[pc++].toUByte()
    }

    fun readInt8(): Byte {
        return readUint8().toByte()
    }

    fun readUint16(): UShort {
        val low = code[pc++].toUByte().toUInt()  // 符号位不填充高位
        val high = code[pc++].toUByte().toUInt()
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
        return ((b4 shl 24) or (b3 shl 16) or (b2 shl 8) or b1).toInt()
    }

}
