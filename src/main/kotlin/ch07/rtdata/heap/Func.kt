package ch07.rtdata.heap;

import ch07.rtdata.heap.KvmClass;

/**
 * 查找继承树中符合条件的方法
 */
fun lookupMethodInClass(klass: KvmClass, name: String, descriptor: String): KvmMethod? {
    var clazz: KvmClass? = klass
    while (clazz != null) {
        clazz.methods.forEach { m ->
            if (m.name == name && m.descriptor == descriptor) {
                return m
            }
        }
        clazz = clazz.superClass
    }
    return null
}