package ch03.classfile

import kotlin.UByte
import kotlin.fromBits

class ConstantPool {
    private var data: Array<ConstantInfo?> = emptyArray()

    fun getConstantInfo(index: UShort): ConstantInfo {
        TODO()
    }

    fun getNameAndType(index: UShort): Array<String> {
        TODO()
    }

    fun getClassName(uShort: UShort): String {
        TODO()
    }

    fun getUtf8(index: UShort): String {
        TODO("Not yet implemented")
    }

    companion object {
        @JvmStatic
        fun readConstantPool(reader: ClassReader): ConstantPool {
            val constantPool = ConstantPool()
            val constantCount = reader.readUint16().toInt()
            val constantInfoList = mutableListOf<ConstantInfo>()
            var i = 1
            while (i <= constantCount) {
                val ci = readConstantInfo(reader, constantPool)
                constantInfoList.add(ci)

                // TODO: 可能有问题，源码见书 P36
                if (ci is ConstantLongInfo || ci is ConstantDoubleInfo) {
                    i += 2
                } else {
                    i++
                }
            }

            constantPool.data = constantInfoList.toTypedArray()
            return constantPool
        }

        @JvmStatic
        fun readConstantInfo(reader: ClassReader, constantPool: ConstantPool): ConstantInfo {
            TODO()
        }

    }
}

