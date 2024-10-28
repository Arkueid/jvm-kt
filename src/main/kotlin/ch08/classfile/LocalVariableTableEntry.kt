package ch08.classfile

class LocalVariableTableEntry(
    val startPc: UShort,
    val length: UShort,
    val nameIndex: UShort,
    val descriptorIndex: UShort,
    val index: UShort,
)
