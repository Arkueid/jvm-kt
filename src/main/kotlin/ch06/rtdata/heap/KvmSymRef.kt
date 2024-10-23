package ch06.rtdata.heap

import ch06.classfile.KvmClass
import sun.jvm.hotspot.runtime.PerfMemory.initialized

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
        val d = cp.klass // 使用符号引用 当前类
        val c = d.loader.loadClass(className) // 解析当前类
        if (!c.isAccessibleTo(d)) { // 当前类对 使用引用的类 不可见
            throw RuntimeException("java.lang.IllegalAccessError")
        }
        klass = c
    }
}