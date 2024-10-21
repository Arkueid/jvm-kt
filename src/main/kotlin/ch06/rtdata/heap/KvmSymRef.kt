package ch06.rtdata.heap

import ch06.classfile.KvmClass

// 符号引用
// 一个类在被加载时，如果它用到了其它类，可以保留符号引用，暂时不加载真正的类数据
open class KvmSymRef: KvmConstant {
    lateinit var cp: KvmConstantPool
    lateinit var className: String
    lateinit var klass: KvmClass
}