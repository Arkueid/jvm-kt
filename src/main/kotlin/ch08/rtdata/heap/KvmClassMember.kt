package ch08.rtdata.heap

import ch08.rtdata.heap.KvmClass
import ch08.classfile.MemberInfo


open class KvmClassMember {
    var accessFlags: UShort = 0u
    lateinit var name: String
    lateinit var descriptor: String
    lateinit var klass: KvmClass

    fun copyMemberInfo(memberInfo: MemberInfo) {
        accessFlags = memberInfo.accessFlags
        name = memberInfo.name
        descriptor = memberInfo.descriptor
    }

    val isPublic get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_PUBLIC)
    val isPrivate get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_PRIVATE)
    val isProtected get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_PROTECTED)
    val isStatic get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_STATIC)
    val isFinal get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_FINAL)
    val isSuper get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_SUPER)
    val isSynchronized get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_SYNCHRONIZED)
    val isVolatile get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_VOLATILE)
    val isBridge get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_BRIDGE)
    val isTransient get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_TRANSIENT)
    val isVarargs get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_VARARGS)
    val isNative get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_NATIVE)
    val isInterface get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_INTERFACE)
    val isAbstract get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_ABSTRACT)
    val isStrict get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_STRICT)
    val isSynthetic get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_SYNTHETIC)
    val isAnnotation get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_ANNOTATION)
    val isEnum get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_ENUM)

    fun isAccessibleTo(d: KvmClass): Boolean {
        if (isPublic) return true

        val c = klass
        if (isProtected) {
            return d == c || d.isSubClassOf(c) || c.packageName == d.packageName
        }

        if (!isPrivate) {
            return c.packageName == d.packageName
        }

        return d == c
    }
}