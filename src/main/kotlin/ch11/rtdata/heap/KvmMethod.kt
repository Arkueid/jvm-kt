package ch11.rtdata.heap

import ch11.classfile.ExceptionsAttribute
import ch11.classfile.LineNumberTableAttribute
import ch11.classfile.MemberInfo
import ch11.rtdata.heap.KvmMethodDescriptorParser.Companion.parseMethodDescriptor

open class KvmMethod : KvmClassMember() {
    var maxStack: UInt = 0u
    var maxLocals: UInt = 0u
    lateinit var code: ByteArray
    var exceptions: ExceptionsAttribute? = null
    var exceptionTable: KvmExceptionTable = emptyArray()
    var lineNumberTable: LineNumberTableAttribute? = null
    lateinit var parsedDescriptor: KvmMethodDescriptor

    var parameterAnnotationData: ByteArray? = null
    var annotationDefaultData: ByteArray? = null

    companion object {
        @JvmStatic
        fun newMethods(klass: KvmClass, cfMethods: Array<MemberInfo>): Array<KvmMethod> {
            return Array(cfMethods.size) { newMethod(klass, cfMethods[it]) }
        }

        @JvmStatic
        fun newMethod(clazz: KvmClass, cfMethod: MemberInfo): KvmMethod {
            val method = KvmMethod().apply {
                klass = clazz
                copyMemberInfo(cfMethod)
                copyAttributes(cfMethod)
            }
            val md = parseMethodDescriptor(method.descriptor)
            method.calcArgSlotCount(md.parameterTypes)
            if (method.isNative) {
                method.injectCodeAttribute(md.returnType)
            }
            method.parsedDescriptor = md
            return method
        }
    }

    private fun injectCodeAttribute(returnType: String) {
        maxStack = 4u
        maxLocals = argSlotCount.toUInt()
        when (returnType[0]) {
            'V' -> code = byteArrayOf(0xfe.toByte(), 0xb1.toByte())
            'D' -> code = byteArrayOf(0xfe.toByte(), 0xaf.toByte())
            'F' -> code = byteArrayOf(0xfe.toByte(), 0xae.toByte())
            'J' -> code = byteArrayOf(0xfe.toByte(), 0xad.toByte())
            'L', '[' -> code = byteArrayOf(0xfe.toByte(), 0xb0.toByte())
            else -> code = byteArrayOf(0xfe.toByte(), 0xac.toByte())

        }
    }

    private fun calcArgSlotCount(parameterTypes: List<String>) {
        parameterTypes.forEach { paramType ->
            _argSlotCount++
            if (paramType == "J" || paramType == "D") {
                _argSlotCount++
            }
        }
        if (!isStatic) { // 自身不是静态方法，则需要实例引用
            _argSlotCount++
        }
    }

    private fun copyAttributes(info: MemberInfo) {
        info.codeAttribute?.let {
            maxStack = it.maxStack.toUInt()
            maxLocals = it.maxLocals.toUInt()
            code = it.code
            exceptionTable = newKvmExceptionTable(it.exceptionTable, klass.constantPool)
            lineNumberTable = it.lineNumberTable
        }
        exceptions = info.exceptionAttribute
        annotationData = info.runtimeVisibleAnnotationsAttributeData
        parameterAnnotationData = info.runtimeVisibleParameterAnnotationsAttributeData
        annotationDefaultData = info.annotationDefaultAttributeData
    }

    fun findExceptionHandler(klass: KvmClass, pc: Int): Int {
        val handler = exceptionTable.findExceptionHandler(klass, pc)
        if (handler != null) {
            return handler.handlerPc
        }
        return -1
    }

    private var _argSlotCount: Int = 0
    val argSlotCount get() = _argSlotCount

    val isConstructor: Boolean get() = !isStatic && name == "<init>"

    val isClinit: Boolean get() = isStatic && name == "<clinit>"

    fun getLineNumber(pc: Int): Int {
        if (isNative) {
            return -2
        }
        if (lineNumberTable == null) {
            return -1
        }

        return lineNumberTable!!.getLineNumber(pc)
    }

    val parameterTypes: Array<KvmClass>?
        get() {
            if (_argSlotCount == 0) {
                return null
            }

            val paramTypes = parsedDescriptor.parameterTypes
            return Array<KvmClass>(paramTypes.size) {
                val paramClassName = klass.toClassName(paramTypes[it])
                klass.loader.loadClass(paramClassName)
            }
        }

    @OptIn(ExperimentalUnsignedTypes::class)
    val exceptionTypes: Array<KvmClass>?
        get() {
            if (exceptions == null) {
                return null
            }

            val exIndexTable = exceptions!!.exceptionIndexTable!!
            val cp = klass.constantPool
            return Array<KvmClass>(exIndexTable.size) {
                val classRef = cp.getConstant(exIndexTable[it].toUInt()) as KvmClassRef
                classRef.resolvedClass
            }
        }

    val returnType: KvmClass
        get() = klass.run {
            loader.loadClass(toClassName(parsedDescriptor.returnType))
        }
}