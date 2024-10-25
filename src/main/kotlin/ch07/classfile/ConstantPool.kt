package ch07.classfile

class ConstantPool {
    private var data: Array<ConstantInfo?> = emptyArray()

    val size: Int
        get() = data.size

    fun getConstantInfo(index: UShort): ConstantInfo = data[index.toInt()]!!

    fun getNameAndType(index: UShort): Array<String> =
        (data[index.toInt()]!! as ConstantNameAndTypeInfo).run {
            arrayOf(getUtf8(nameIndex), getUtf8(descriptorIndex))
        }

    fun getClassName(index: UShort): String = (getConstantInfo(index) as ConstantClassInfo).run {
        getUtf8(nameIndex)
    }

    fun getUtf8(index: UShort): String = (getConstantInfo(index) as ConstantUtf8Info).value

    operator fun get(index: Int) = data[index]

    operator fun set(index: Int, value: ConstantInfo) {
        data[index] = value
    }

    companion object {
        @JvmStatic
        fun readConstantPool(reader: ClassReader): ConstantPool {
            val constantPool = ConstantPool()
            val constantCount = reader.readUint16().toInt()
            constantPool.data = Array(constantCount) { null }
            var i = 1
            while (i < constantCount) {
                val ci = readConstantInfo(reader, constantPool)
                constantPool[i] = ci

                if (ci is ConstantLongInfo || ci is ConstantDoubleInfo) {
                    i += 2
                } else {
                    i++
                }
            }

            return constantPool
        }

        @JvmStatic
        fun readConstantInfo(reader: ClassReader, constantPool: ConstantPool): ConstantInfo {
            return newConstantInfo(reader, constantPool).apply {
                readInfo(reader)
            }
        }

    }
}

