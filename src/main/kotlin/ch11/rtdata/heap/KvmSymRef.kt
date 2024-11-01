package ch11.rtdata.heap

import ch11.rtdata.heap.KvmClass

// 符号引用
// 一个类在被加载时，如果它用到了其它类，可以保留符号引用，暂时不加载真正的类数据
open class KvmSymRef : KvmConstant {
    lateinit var cp: KvmConstantPool
    lateinit var className: String
    lateinit var klass: KvmClass

    val resolvedClass: KvmClass
        get() {
            if (!::klass.isInitialized) {
                resolveClassRef()
            }
            return klass
        }

    private fun resolveClassRef() {
        val d = cp.klass // 正在执行的方法所在的类，暂称为 当前类
        val c = d.loader.loadClass(className) // 引用指向的类，暂称为被引用类
        if (!c.isAccessibleTo(d)) { // 被引用的类对 当前类 不可见
            throw RuntimeException("java.lang.IllegalAccessError")
        }
        klass = c
    }
}