package ch11.rtdata.heap

val NODATA = emptyArray<Any?>()

class KvmObject(
    // TODO: 默认参数，方便测试
    val klass: KvmClass = KvmClass(),
    private val _fields: KvmSlots = KvmSlots(0u),
    val data: Array<out Any?> = NODATA,
) {
    // 类实例/类对象 --> obj.getClass()/obj.class 得到的对象
    lateinit var extraClass: KvmClass

    // TODO
    var extraField: KvmField? = null

    // TODO
    var extraMethod: KvmMethod? = null

    lateinit var exceptionInfo: Array<KvmStackTraceElement>

    fun setRefVar(name: String, descriptor: String, ref: KvmObject) {
        val field = getField(name, descriptor)
        _fields.setRef(field.slotId, ref)
    }

    fun getRefVar(name: String, descriptor: String): KvmObject {
        val field = getField(name, descriptor)
        return _fields.getRef(field.slotId)!!
    }

    fun getIntVar(name: String, descriptor: String): Int {
        val field = getField(name, descriptor)
        return _fields.getInt(field.slotId)
    }

    fun setIntVar(name: String, descriptor: String, value: Int) {
        val field = getField(name, descriptor)
        return _fields.setInt(field.slotId, value)
    }

    private fun getField(name: String, descriptor: String): KvmField {
        val field = klass.getField(name, descriptor, false)
        if (field == null) {
            throw RuntimeException("NoSuchFieldException: ${klass.name}.$name$descriptor")
        }
        return field
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

