package ch07.rtdata.heap

import ch07.classfile.ConstantClassInfo
import ch07.classfile.ConstantDoubleInfo
import ch07.classfile.ConstantFieldRefInfo
import ch07.classfile.ConstantFloatInfo
import ch07.classfile.ConstantIntegerInfo
import ch07.classfile.ConstantInterfaceMethodRefInfo
import ch07.classfile.ConstantLongInfo
import ch07.classfile.ConstantMethodRefInfo
import ch07.classfile.ConstantPool
import ch07.classfile.ConstantStringInfo
import ch07.rtdata.heap.KvmClass

interface KvmConstant

fun KvmConstant.getInt(): Int = (this as KvmInt).value
fun KvmConstant.getFloat(): Float = (this as KvmFloat).value
fun KvmConstant.getDouble(): Double = (this as KvmDouble).value
fun KvmConstant.getLong(): Long = (this as KvmLong).value
fun KvmConstant.getString(): String = (this as KvmString).value

class KvmConstantPool {
    var klass: KvmClass
    var constants: Array<KvmConstant?>

    constructor(klass: KvmClass, cfCp: ConstantPool) {
        this.klass = klass
        constants = Array(cfCp.size) { null }
        var i = 1
        while (i < cfCp.size) {
            cfCp[i]?.let { info ->
                constants[i] = when (info) {
                    is ConstantIntegerInfo -> KvmInt(info.value)
                    is ConstantFloatInfo -> KvmFloat(info.value)
                    is ConstantDoubleInfo -> KvmDouble(info.value).also { i++ }
                    is ConstantLongInfo -> KvmLong(info.value).also { i++ }
                    is ConstantStringInfo -> KvmString(info.String())
                    is ConstantClassInfo -> KvmClassRef(this, info)
                    is ConstantFieldRefInfo -> KvmFieldRef(this, info)
                    is ConstantMethodRefInfo -> KvmMethodRef(this, info)
                    is ConstantInterfaceMethodRefInfo -> KvmInterfaceMethodRef(this, info)
                    else -> null
                }
            }
            i++
        }
    }

    fun getConstant(index: UInt): KvmConstant {
        return constants[index.toInt()] ?: throw RuntimeException("No constants at index $index")
    }
}