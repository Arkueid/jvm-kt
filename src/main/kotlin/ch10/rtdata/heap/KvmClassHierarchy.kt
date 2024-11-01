package ch10.rtdata.heap

fun KvmObject.isInstanceOf(klass: KvmClass): Boolean {
    return klass.isAssignableFrom(klass)
}

/**
 * src 是否可以赋值到 类型为 target 的引用上
 */
fun KvmClass.isAssignableFrom(other: KvmClass): Boolean {
    val s = other
    val t = this
    // 同一个类
    if (s == t) {
        return true
    }

    if (!s.isArray) {
        return if (!s.isInterface) {
            if (!t.isInterface) {
                s.isSubClassOf(t)
            } else {
                s.isImplements(t)
            }
        } else {
            if (!t.isInterface) { // 接口的父类不是接口就只可能是 Object
                t.isJlObject
            } else {
                t.isSuperInterfaceOf(s)
            }
        }
    } else {
        if (!t.isArray) {
            return if (!t.isInterface) {
                t.isJlObject
            } else {
                t.isJlCloneable || t.isJioSerializable
            }
        } else {
            val sc = s.componentClass
            val tc = t.componentClass
            return tc == sc || tc.isAssignableFrom(sc)
        }
    }
    return false
}

fun KvmClass.isSuperInterfaceOf(iface: KvmClass): Boolean {
    return iface.isSubInterfaceOf(this)
}

fun KvmClass.isSubInterfaceOf(iface: KvmClass): Boolean {
    interfaces.forEach { clazz ->
        if (clazz == iface || clazz.isSubInterfaceOf(iface)) {
            return true
        }
    }
    return false
}

fun KvmClass.isImplements(iface: KvmClass): Boolean {
    var clazz: KvmClass? = this
    while (clazz != null) {
        if (clazz == iface || clazz.isSubInterfaceOf(iface)) {
            return true
        }
        clazz = clazz.superClass
    }
    return false
}

fun KvmClass.isSubClassOf(klass: KvmClass): Boolean {
    var clazz = superClass
    while (clazz != null) {
        if (clazz == klass) {
            return true
        }
        clazz = clazz.superClass
    }
    return false
}

fun KvmClass.isSuperClassOf(other: KvmClass): Boolean {
    return other.isSubClassOf(this)
}


