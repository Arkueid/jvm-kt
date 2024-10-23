package ch06.classfile

import ch06.rtdata.heap.KvmAccessFlags
import ch06.rtdata.heap.KvmField
import ch06.rtdata.heap.KvmMethod
import ch06.rtdata.heap.KvmClassLoader
import ch06.rtdata.heap.KvmConstantPool
import ch06.rtdata.heap.KvmObject
import ch06.rtdata.heap.KvmSlots

class KvmClass(
    val accessFlags: UShort,
    val name: String,
    val superClassName: String,
    val interfaceNames: Array<String>,
) {
    lateinit var constantPool: KvmConstantPool

    lateinit var fields: Array<KvmField>
    lateinit var methods: Array<KvmMethod>
    lateinit var loader: KvmClassLoader
    var superClass: KvmClass? = null
    lateinit var interfaces: Array<KvmClass>

    // 成员变量槽，每个实例独享一份
    var instanceSlotCount: UInt = 0u

    // 静态成员变量槽，同一个类的所有实例共享
    var staticSlotCount: UInt = 0u
    lateinit var staticVars: KvmSlots

    constructor(classFile: ClassFile) : this(
        classFile.accessFlags,
        classFile.className,
        classFile.superClassName,
        classFile.interfaceNames
    ) {
        constantPool = KvmConstantPool(this, classFile.constantPool)
        fields = KvmField.createFields(this, classFile.fields)
        methods = KvmMethod.createMethods(this, classFile.methods)
    }

    val packageName: String
        get() {
            return name.indexOfLast { it == '/' }.let { index ->
                if (index >= 0) name.substring(0 until index) else ""
            }
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

    // 对另一个类可见，首先被 public 标识，并且在同一个包下
    fun isAccessibleTo(other: KvmClass): Boolean = isPublic && packageName == other.packageName

    fun isSubClassOf(klass: KvmClass): Boolean {
        TODO("Not yet implemented")
    }

    fun newObject(): KvmObject {
        return KvmObject(this, KvmSlots(this.instanceSlotCount.toUInt()))
    }
}
