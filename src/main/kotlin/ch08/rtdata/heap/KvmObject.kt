package ch08.rtdata.heap

class KvmObject(
    val klass: KvmClass,
    private val _fields: KvmSlots = KvmSlots(0u),
    val data: Array<out Any?> = emptyArray(),
) {
    fun setRefVar(fieldName: String, fieldDescriptor: String, ref: KvmObject) {
        val field = klass.getField(fieldName, fieldDescriptor, false)
        if (field == null) {
            throw RuntimeException("NoSuchFieldException: ${klass.name}.$fieldName$fieldDescriptor")
        }
        _fields.setRef(field.slotId, ref)
    }

    fun getRefVar(fieldName: String, fieldDescriptor: String): KvmObject {
        val field = klass.getField(fieldName, fieldDescriptor, false)
        if (field == null) {
            throw RuntimeException("NoSuchFieldException: ${klass.name}.$fieldName$fieldDescriptor")
        }
        return fields.getRef(field.slotId)!!
    }

    val fields get() = _fields

}

