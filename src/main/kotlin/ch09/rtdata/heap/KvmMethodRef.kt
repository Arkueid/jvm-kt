package ch09.rtdata.heap

import ch09.classfile.ConstantMethodRefInfo
import ch09.rtdata.heap.KvmClass

/**
 * 非接口方法引用
 */
class KvmMethodRef(rtCp: KvmConstantPool, methodRefInfo: ConstantMethodRefInfo) : KvmMemberRef() {
    private lateinit var method: KvmMethod

    init {
        cp = rtCp
        copyMemberRefInfo(methodRefInfo)
    }

    val resolvedMethod: KvmMethod
        get() {
            if (!::method.isInitialized) {
                resolveMethodRef()
            }
            return method
        }

    private fun resolveMethodRef() {
        // 当前方法所属的类
        val d = cp.klass
        // 被调用方法所属的类
        val c = resolvedClass
        if (c.isInterface) { // 当前方法是接口方法，未被实现
            throw RuntimeException("java.lang.IncompatibleClassChangeError")
        }

        val method = lookupMethod(c, name, descriptor)

        if (method == null) {
            throw RuntimeException("java.lang.NoSuchMethodError")
        }

        if (!method.isAccessibleTo(d)) { // 当前正在执行的方法是否有权限调用 method
            throw RuntimeException("java.lang.IllegalAccessError")
        }

        this.method = method
    }

    private fun lookupMethod(klass: KvmClass, name: String, descriptor: String): KvmMethod? {
        var method = lookupMethodInClass(klass, name, descriptor)
        if (method == null) {
            method = lookupMethodInInterfaces(klass.interfaces, name, descriptor)
        }
        return method
    }

    /**
     * 从实现的接口中寻找目标方法
     */
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
            // 接口的继承关系中查找
            val method = lookupMethodInInterfaces(iface.interfaces, name, descriptor)
            if (method != null) {
                return method
            }
        }
        return null
    }
}