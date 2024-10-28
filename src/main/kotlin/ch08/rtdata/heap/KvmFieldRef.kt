package ch08.rtdata.heap

import ch08.classfile.ConstantFieldRefInfo
import ch08.rtdata.heap.KvmClass

class KvmFieldRef(rtCp: KvmConstantPool, fieldRefInfo: ConstantFieldRefInfo) : KvmMemberRef() {
    private lateinit var kvmField: KvmField

    init {
        cp = rtCp
        copyMemberRefInfo(fieldRefInfo)
    }

    val resolvedField: KvmField
        get() {
            if (!::kvmField.isInitialized) {
                resolveFieldRef()
            }
            return kvmField
        }

    private fun resolveFieldRef() {
        val d = cp.klass
        val c = resolvedClass
        var field = lookupField(c, name, descriptor)

        if (field == null) {
            throw RuntimeException("java.lang.NoSuchFieldError")
        }

        if (!field.isAccessibleTo(d)) {
            throw RuntimeException("java.lang.IllegalAccessError")
        }

        kvmField = field
    }

    private fun lookupField(
        c: KvmClass,
        name: String,
        descriptor: String
    ): KvmField? {
        c.fields.forEach { field ->
            if (field.name == name && field.descriptor == descriptor) {
                return field
            }
        }

        c.interfaces.forEach { iface ->
            lookupField(iface, name, descriptor)?.let {
                return it
            }
        }

        return c.superClass?.let {
            lookupField(it, name, descriptor)
        }
    }
}