package ch11.rtdata.heap

import ch08.rtdata.heap.KvmClass.Companion.primitiveTypes
import ch11.classfile.ClassFile
import ch11.classpath.Classpath
import ch11.classpath.Entry

class KvmClassLoader(val cp: Classpath, val verboseFlag: Boolean = false) {
    val classMap: MutableMap<String, KvmClass> = mutableMapOf()

    init {
        loadBasicClasses()
        loadPrimitiveClasses()
    }

    private fun loadPrimitiveClasses() {
        primitiveTypes.forEach { primitiveType, _ ->
            loadPrimitiveClass(primitiveType)
        }
    }

    private fun loadPrimitiveClass(className: String) =
        KvmClass(KvmAccessFlags.ACC_PUBLIC, className, this, true).let { klass ->
            // java.lang.Class 一定先被加载
            klass.jClass = classMap["java/lang/Class"]!!.newObject()
            klass.jClass!!.extra = klass
            classMap[className] = klass
        }


    private fun loadBasicClasses() {
        val jlClassClass = loadClass("java/lang/Class")
        classMap.forEach { string, klass ->
            if (klass.jClass == null) {
                klass.jClass = jlClassClass.newObject()
                klass.jClass!!.extra = klass
            }
        }
    }

    fun loadClass(name: String): KvmClass {
        return classMap[name] ?: run {
            if (name[0] == '[') {
                loadArrayClass(name)
            } else {
                loadNonArrayClass(name)
            }.apply { // 正在被加载的类
                classMap["java/lang/Class"]?.let { jlClassClass -> // 连接上对应的类实例
                    this.jClass = jlClassClass.newObject()
                    this.jClass!!.extra = this
                }
            }
        }
    }

    private fun loadArrayClass(name: String): KvmClass {
        val klass = KvmClass(
            KvmAccessFlags.ACC_PUBLIC, name, this, true, loadClass("java/lang/Object"), arrayOf(
                loadClass("java/lang/Cloneable"),
                loadClass("java/io/Serializable"),
            )
        )
        classMap[name] = klass
        return klass
    }

    private fun loadNonArrayClass(name: String): KvmClass {
        val result = readClass(name)
        val klass = defineClass(result.data)
        link(klass)
        if (verboseFlag) {
            println("[Loaded $name from ${result.entry.String()}]")
        }
        return klass
    }

    private fun link(klass: KvmClass) {
        verify(klass)
        prepare(klass)
    }

    private fun prepare(klass: KvmClass) {
        // 计算所需内存
        calcSlotIds(klass)
        // 初始化静态成员
        allocAndInitStaticVars(klass)
    }

    private fun allocAndInitStaticVars(klass: KvmClass) {
        // TODO: uint to int
        klass.staticVars = KvmSlots(klass.staticSlotCount)
        klass.fields.forEach { field ->
            if (field.isStatic && field.isFinal) {
                initStaticFinalVars(klass, field)
            }
        }
    }

    private fun initStaticFinalVars(klass: KvmClass, field: KvmField) {
        val vars = klass.staticVars
        val cp = klass.constantPool
        val cpIndex = field.constantValueIndex
        val slotId = field.slotId
        if (cpIndex > 0u) {
            when (field.descriptor) {
                // boolean byte char short int
                "Z", "B", "C", "S", "I" -> vars.setInt(slotId, cp.getConstant(cpIndex).getInt())
                "J" -> vars.setLong(slotId, cp.getConstant(cpIndex).getLong())
                "F" -> vars.setFloat(slotId, cp.getConstant(cpIndex).getFloat())
                "D" -> vars.setDouble(slotId, cp.getConstant(cpIndex).getDouble())
                "Ljava/lang/String;" -> {
                    val ktStr = cp.getConstant(cpIndex).getString()
                    val jStr = kvmJString(klass.loader, ktStr)
                    vars.setRef(slotId, jStr)
                }
            }
        }
    }

    private fun calcSlotIds(klass: KvmClass) {
        // instance 和 static 分开计算
        var instanceSlotId: UInt = klass.superClass?.instanceSlotCount ?: 0u
        var staticSlotId: UInt = 0u
        klass.fields.forEach { field ->
            if (!field.isStatic) { // 静态变量，同一个类的所有实例共有
                field.slotId = instanceSlotId
                instanceSlotId++
                if (field.isLongOrDouble) {
                    instanceSlotId++
                }
            } else { // 成员变量，实例私有
                field.slotId = staticSlotId
                staticSlotId++
                if (field.isLongOrDouble) {
                    staticSlotId++
                }
            }
        }
        klass.instanceSlotCount = instanceSlotId
        klass.staticSlotCount = staticSlotId
    }

    private fun verify(klass: KvmClass) {
        // TODO 参考书目未做实现。Java 虚拟机规范 4.10 节
        //  保证类符合当前虚拟机要求，不会损害虚拟机自身安全
        //  文件格式验证，元数据验证，字节码验证和符号引用验证
    }

    private fun defineClass(bytes: ByteArray): KvmClass {
        val klass = parseClass(bytes)
        klass.loader = this
        resolveSuperClass(klass)
        resolveInterfaces(klass)
        classMap[klass.name] = klass
        return klass
    }

    private fun resolveInterfaces(klass: KvmClass) {
        klass.interfaces = Array(klass.interfaceNames.size) {
            klass.loader.loadClass(klass.interfaceNames[it])
        }
    }

    private fun resolveSuperClass(klass: KvmClass) {
        if (klass.name != "java/lang/Object") {
            klass.superClass = klass.loader.loadClass(klass.superClassName)
        }
    }

    private fun parseClass(bytes: ByteArray): KvmClass {
        val result = ClassFile.parse(bytes)
        if (result.error != null) {
            throw RuntimeException("java.lang.ClassFormatError")
        }

        return KvmClass(result.classFile!!)
    }

    private fun readClass(name: String): ReadClassResult {
        val result = cp.readClass(name)
        if (result.error != null) {
            throw RuntimeException("java.lang.ClassNotFoundException: $name")
        }

        return ReadClassResult(result.data!!, result.entry!!)
    }


}

private class ReadClassResult(
    val data: ByteArray,
    val entry: Entry,
)