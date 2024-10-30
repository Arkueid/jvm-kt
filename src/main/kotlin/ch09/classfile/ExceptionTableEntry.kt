package ch09.classfile

class ExceptionTableEntry (
    var startPc: UShort,
    var endPc: UShort,
    var handlerPc: UShort,
    var catchType: UShort,
)
