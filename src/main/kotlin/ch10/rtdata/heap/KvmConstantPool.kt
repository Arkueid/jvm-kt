package ch10.rtdata.heap

import ch10.classfile.ConstantClassInfo
import ch10.classfile.ConstantDoubleInfo
import ch10.classfile.ConstantFieldRefInfo
import ch10.classfile.ConstantFloatInfo
import ch10.classfile.ConstantIntegerInfo
import ch10.classfile.ConstantInterfaceMethodRefInfo
import ch10.classfile.ConstantLongInfo
import ch10.classfile.ConstantMethodRefInfo
import ch10.classfile.ConstantPool
import ch10.classfile.ConstantStringInfo

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