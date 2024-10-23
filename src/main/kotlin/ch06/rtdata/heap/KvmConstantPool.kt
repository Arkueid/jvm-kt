package ch06.rtdata.heap

import ch06.classfile.ConstantClassInfo
import ch06.classfile.ConstantDoubleInfo
import ch06.classfile.ConstantFieldRefInfo
import ch06.classfile.ConstantFloatInfo
import ch06.classfile.ConstantIntegerInfo
import ch06.classfile.ConstantInterfaceMethodRefInfo
import ch06.classfile.ConstantLongInfo
import ch06.classfile.ConstantMethodRefInfo
import ch06.classfile.ConstantPool
import ch06.classfile.ConstantStringInfo
import ch06.classfile.KvmClass

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
        var i = 0
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