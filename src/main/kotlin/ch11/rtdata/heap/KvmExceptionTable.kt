package ch11.rtdata.heap

import ch11.classfile.ExceptionTableEntry

class KvmExceptionHandler(
    val startPc: Int,
    val endPc: Int,
    val handlerPc: Int,
    val catchType: KvmClassRef?,
)

typealias KvmExceptionTable = Array<KvmExceptionHandler>

fun newKvmExceptionTable(table: Array<ExceptionTableEntry>, cp: KvmConstantPool): KvmExceptionTable {
    return Array<KvmExceptionHandler>(table.size) {
        KvmExceptionHandler(
            table[it].startPc.toInt(),
            table[it].endPc.toInt(),
            table[it].handlerPc.toInt(),
            getCatchType(table[it].catchType.toUInt(), cp)
        )
    }
}

private fun getCatchType(index: UInt, cp: KvmConstantPool): KvmClassRef? {
    if (index == 0u) { // 处理所有异常
        return null
    }
    return cp.getConstant(index) as KvmClassRef
}

fun KvmExceptionTable.findExceptionHandler(exClass: KvmClass, pc: Int): KvmExceptionHandler? {
    forEach { handler ->
        if (pc >= handler.startPc && pc < handler.endPc) {
            if (handler.catchType == null) {
                return handler // 处理范围内的所有exception
            }
            val catchClass = handler.catchType.resolvedClass
            if (catchClass == exClass || catchClass.isSuperClassOf(exClass)) {
                return handler
            }
        }
    }
    return null
}