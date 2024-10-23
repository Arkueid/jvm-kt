package ch06.rtdata.heap

import ch06.classfile.KvmClass
import ch06.classfile.MemberInfo


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

    val isPublic = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_PUBLIC)
    val isPrivate = 0.toUShort() != accessFlags and KvmAccessFlags.ACC_PRIVATE
    val isProtected = 0.toUShort() != accessFlags and KvmAccessFlags.ACC_PROTECTED
    val isStatic = 0.toUShort() != accessFlags and KvmAccessFlags.ACC_STATIC
    val isFinal = 0.toUShort() != accessFlags and KvmAccessFlags.ACC_FINAL
    val isSuper = 0.toUShort() != accessFlags and KvmAccessFlags.ACC_SUPER
    val isSynchronized = 0.toUShort() != accessFlags and KvmAccessFlags.ACC_SYNCHRONIZED
    val isVolatile = 0.toUShort() != accessFlags and KvmAccessFlags.ACC_VOLATILE
    val isBridge = 0.toUShort() != accessFlags and KvmAccessFlags.ACC_BRIDGE
    val isTransient = 0.toUShort() != accessFlags and KvmAccessFlags.ACC_TRANSIENT
    val isVarargs = 0.toUShort() != accessFlags and KvmAccessFlags.ACC_VARARGS
    val isNative = 0.toUShort() != accessFlags and KvmAccessFlags.ACC_NATIVE
    val isInterface = 0.toUShort() != accessFlags and KvmAccessFlags.ACC_INTERFACE
    val isAbstract = 0.toUShort() != accessFlags and KvmAccessFlags.ACC_ABSTRACT
    val isStrict = 0.toUShort() != accessFlags and KvmAccessFlags.ACC_STRICT
    val isSynthetic = 0.toUShort() != accessFlags and KvmAccessFlags.ACC_SYNTHETIC
    val isAnnotation = 0.toUShort() != accessFlags and KvmAccessFlags.ACC_ANNOTATION
    val isEnum = 0.toUShort() != accessFlags and KvmAccessFlags.ACC_ENUM

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