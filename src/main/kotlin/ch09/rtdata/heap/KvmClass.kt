package ch09.rtdata.heap

import ch09.classfile.ClassFile

class KvmClass() {
    var accessFlags: UShort = 0u
    lateinit var name: String
    lateinit var superClassName: String
    lateinit var interfaceNames: Array<String>

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

    constructor(
        accessFlags: UShort,
        name: String,
        superClassName: String,
        interfaceNames: Array<String>
    ) : this() {
        this.accessFlags = accessFlags
        this.name = name
        this.superClassName = superClassName
        this.interfaceNames = interfaceNames
    }

    constructor(
        accessFlags: UShort,
        name: String,
        loader: KvmClassLoader,
        initStarted: Boolean,
        superClass: KvmClass,
        interfaces: Array<KvmClass>
    ) : this() {
        this.accessFlags = accessFlags
        this.name = name
        this.loader = loader
        this._initStarted = initStarted
        this.superClass = superClass
        this.interfaces = interfaces
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

    fun newObject(): KvmObject {
        return KvmObject(this, KvmSlots(this.instanceSlotCount.toUInt()))
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

    fun getClinitMethod(): KvmMethod? = getStaticMethod("<clinit>", "()V")

    val arrayClass: KvmClass
        get() {
            val arrayClassName = getArrayClassName(name)
            return loader.loadClass(arrayClassName)
        }

    private fun getArrayClassName(name: String): String = "[" + toDescriptor(name)

    val primitiveTypes = mapOf<String, String>(
        Pair("void", "V"),
        Pair("boolean", "Z"),
        Pair("byte", "B"),
        Pair("short", "S"),
        Pair("int", "I"),
        Pair("long", "J"),
        Pair("char", "C"),
        Pair("float", "F"),
        Pair("double", "D"),
    )

    private fun toDescriptor(name: String): String {
        if (name[0] == '[') {
            return name
        }

        val d = primitiveTypes[name]
        if (d != null) {
            return d
        }

        return "L$name;"
    }

    fun getField(fieldName: String, fieldDescriptor: String, isStatic: Boolean): KvmField? {
        var clazz: KvmClass? = this
        while (clazz != null) {
            fields.forEach { field ->
                if (field.isStatic == isStatic && field.name == fieldName && field.descriptor == fieldDescriptor) {
                    return field
                }
            }
            clazz = clazz.superClass
        }
        return null
    }

    val isJlObject: Boolean get() = name == "java/lang/Object"

    val isJlCloneable: Boolean
        get() = name == "java/lang/Cloneable"

    val isJioSerializable: Boolean
        get() = name == "java/io/Serializable"

}


