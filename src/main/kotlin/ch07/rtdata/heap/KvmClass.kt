package ch07.rtdata.heap

import ch07.classfile.ClassFile

class KvmClass(
    val accessFlags: UShort,
    val name: String,
    val superClassName: String,
    val interfaceNames: Array<String>,
) {
    lateinit var constantPool: KvmConstantPool

    private var _initStarted: Boolean = false
    val initStarted: Boolean get() = _initStarted

    fun startInit() {
        _initStarted = true
    }

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

    val isPublic get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_PUBLIC)
    val isPrivate get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_PRIVATE)
    val isProtected get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_PROTECTED)
    val isStatic get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_STATIC)
    val isFinal get() = 0.toUShort() != (accessFlags and KvmAccessFlags.ACC_FINAL)

    // super 位是否为 1，除此外没有其他语义，不表示`是父类`的意思，而是`存在父类`的意思
    // 除了 java/lang/Object，所有类为1
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

    // 对另一个类可见，首先被 public 标识，并且在同一个包下
    fun isAccessibleTo(other: KvmClass): Boolean {
        return isPublic || packageName == other.packageName
    }

    fun isSubClassOf(klass: KvmClass): Boolean {
        var clazz = superClass
        while (clazz != null) {
            if (clazz == klass) {
                return true
            }
            clazz = clazz.superClass
        }
        return false
    }

    fun newObject(): KvmObject {
        return KvmObject(this, KvmSlots(this.instanceSlotCount.toUInt()))
    }

    /**
     * src 是否可以赋值到 类型为 target 的引用上
     */
    fun isAssignableFrom(other: KvmClass): Boolean {
        val s = other
        val t = this
        // 同一个类
        if (s == t) {
            return true
        }

        // 是接口
        return if (t.isInterface) { // 实现了接口
            s.isImplements(t)
        } else { // 是子类
            s.isSubClassOf(t)
        }
    }

    fun isImplements(iface: KvmClass): Boolean {
        var clazz: KvmClass? = this
        while (clazz != null) {
            if (clazz == iface || clazz.isSubInterfaceOf(iface)) {
                return true
            }
            clazz = clazz.superClass
        }
        return false
    }

    private fun isSubInterfaceOf(iface: KvmClass): Boolean {
        interfaces.forEach { clazz ->
            if (clazz == iface || clazz.isSubInterfaceOf(iface)) {
                return true
            }
        }
        return false
    }

    val mainMethod: KvmMethod? get() = getStaticMethod("main", "([Ljava/lang/String;)V")

    private fun getStaticMethod(methodName: String, methodDescriptor: String): KvmMethod? {
        methods.forEach { method ->
            if (method.isStatic && method.name == methodName && method.descriptor == methodDescriptor) {
                return method
            }
        }
        return null
    }

    fun isSuperClassOf(other: KvmClass): Boolean {
        return other.isSubClassOf(this)
    }

    fun getClinitMethod(): KvmMethod? = getStaticMethod("<clinit>", "()V")
}