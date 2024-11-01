package ch09.rtdata.heap

class KvmObject(
    // TODO: 默认参数，方便测试
    val klass: KvmClass = KvmClass(),
    private val _fields: KvmSlots = KvmSlots(0u),
    val data: Array<out Any?> = emptyArray(),
) {
    // 类实例/类对象 --> obj.getClass()/obj.class 得到的对象
    lateinit var extra: KvmClass

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
        return _fields.getRef(field.slotId)!!
    }

    fun clone(): KvmObject = KvmObject(klass, cloneFields(), cloneData())

    private fun cloneFields(): KvmSlots {
        val src = _fields.slots
        return KvmSlots(_fields.slots.size.toUInt()).apply {
            src.forEachIndexed { idx, slot ->
                this.slots[idx].num = slot.num
                this.slots[idx].ref = slot.ref
            }
        }
    }

    private fun cloneData(): Array<Any?> {
        var array = Array<Any?>(data.size) { null }
        System.arraycopy(data, 0, array, 0, data.size)
        return array
    }

    val fields get() = _fields

}

