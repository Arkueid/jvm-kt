package ch07.rtdata.heap

import ch07.classfile.ConstantInterfaceMethodRefInfo
import ch07.rtdata.heap.KvmClass

/**
 * 接口方法引用
 */
class KvmInterfaceMethodRef(rtCp: KvmConstantPool, info: ConstantInterfaceMethodRefInfo) : KvmMemberRef() {
    init {
        cp = rtCp
        copyMemberRefInfo(info)
    }

    private lateinit var method: KvmMethod

    val resolvedInterfaceMethod: KvmMethod
        get() {
            if (!::method.isInitialized) {
                resolveInterfaceMethodRef()
            }
            return method
        }

    private fun resolveInterfaceMethodRef() {
        val d = cp.klass
        val c = resolvedClass
        if (!c.isInterface) {
            throw RuntimeException("java.lang.IncompatibleClassChangeError")
        }

        val method = lookupInterfaceMethod(c, name, descriptor)
        if (method == null) {
            throw RuntimeException("java.lang.NoSuchMethodError")
        }

        if (!method.isAccessibleTo(d)) { // 当前正在执行的方法是否有权限调用 method
            throw RuntimeException("java.lang.IllegalAccessError")
        }

        this.method = method
    }

    private fun lookupInterfaceMethod(iface: KvmClass, name: String, descriptor: String): KvmMethod? {
        iface.methods.forEach { method ->
            if (method.name == name && method.descriptor == descriptor) {
                return method
            }
        }
        return lookupMethodInInterfaces(iface.interfaces, name, descriptor)
    }

    private fun lookupMethodInInterfaces(
        ifaces: Array<KvmClass>,
        name: String,
        descriptor: String
    ): KvmMethod? {
        ifaces.forEach { iface ->
            iface.methods.forEach { method ->
                if (method.name == name && method.descriptor == descriptor) {
                    return method
                }
            }
            val method = lookupMethodInInterfaces(iface.interfaces, name, descriptor)
            if (method != null) {
                return method
            }
        }
        return null
    }
}