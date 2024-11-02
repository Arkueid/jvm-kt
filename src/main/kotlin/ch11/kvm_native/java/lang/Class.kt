package ch11.kvm_native.java.lang

import ch11.instructions.base.initClass
import ch11.instructions.base.invokeMethod
import ch11.kvm_native.KvmNative
import ch11.rtdata.KvmFrame
import ch11.rtdata.KvmOperandStack
import ch11.rtdata.KvmShimFrame
import ch11.rtdata.heap.KvmClass
import ch11.rtdata.heap.KvmClassLoader
import ch11.rtdata.heap.KvmObject
import ch11.rtdata.heap.componentClass
import ch11.rtdata.heap.isArray
import ch11.rtdata.heap.isAssignableFrom
import ch11.rtdata.heap.kvmJString
import ch11.rtdata.heap.kvmStrFromJStr
import ch11.rtdata.heap.newArray
import ch11.rtdata.heap.newByteArray
import ch11.rtdata.heap.refs
import kotlin.String


private const val jlClass = "java/lang/Class"

object Class {
    @JvmStatic
    fun init() {
        KvmNative.register(
            jlClass, "getPrimitiveClass", "(Ljava/lang/String;)Ljava/lang/Class;", ::getPrimitiveClass
        )

        KvmNative.register(
            jlClass, "getName0", "()Ljava/lang/String;", ::getName0
        )

        KvmNative.register(
            jlClass, "desiredAssertionStatus0", "(Ljava/lang/Class;)Z", ::desiredAssertionStatus0
        )

        KvmNative.register(jlClass, "isInterface", "()Z", ::isInterface)
        KvmNative.register(jlClass, "isPrimitive", "()Z", ::isPrimitive)
        KvmNative.register(jlClass, "getDeclaredFields0", "(Z)[Ljava/lang/reflect/Field;", ::getDeclaredFields0)
        KvmNative.register(
            jlClass,
            "forName0",
            "(Ljava/lang/String;ZLjava/lang/ClassLoader;Ljava/lang/Class;)Ljava/lang/Class;",
            ::forName0
        )
        KvmNative.register(
            jlClass, "getDeclaredConstructors0", "(Z)[Ljava/lang/reflect/Constructor;", ::getDeclaredConstructors0
        )
        KvmNative.register(jlClass, "getModifiers", "()I", ::getModifiers)
        KvmNative.register(jlClass, "getSuperclass", "()Ljava/lang/Class;", ::getSuperclass)
        KvmNative.register(jlClass, "getInterfaces0", "()[Ljava/lang/Class;", ::getInterfaces0)
        KvmNative.register(jlClass, "isArray", "()Z", ::isArray)
        KvmNative.register(jlClass, "getDeclaredMethods0", "(Z)[Ljava/lang/reflect/Method;", ::getDeclaredMethods0)
        KvmNative.register(jlClass, "getComponentType", "()Ljava/lang/Class;", ::getComponentType)
        KvmNative.register(jlClass, "isAssignableFrom", "(Ljava/lang/Class;)Z", ::isAssignableFrom)


    }
}


// static KvmNative Class<?> getPrimitiveClass(String name);
private fun getPrimitiveClass(frame: KvmFrame) {
    val nameObj = frame.localVars.getRef(0u)!!
    val name = kvmStrFromJStr(nameObj)!!

    val loader = frame.method.klass.loader
    val jClass = loader.loadClass(name).jClass

    frame.operandStack.pushRef(jClass)
}


// private KvmNative String getName0();
private fun getName0(frame: KvmFrame) {
    val __this = frame.localVars.getRef(0u)!!
    val klass = __this.extraClass

    val name = klass.javaName
    val nameObj = kvmJString(klass.loader, name)

    frame.operandStack.pushRef(nameObj)
}

// TODO: 暂不考虑断言
//  private static KvmNative boolean desiredAssertionStatus0(Class<> clazz);
private fun desiredAssertionStatus0(frame: KvmFrame) {
    frame.operandStack.pushInt(0)  // return false
}

// public native boolean isInterface();
// ()Z
private fun isInterface(frame: KvmFrame) {
    val vars = frame.localVars
    val _this = vars.getRef(0u)!!
    val klass = _this.extraClass

    val stack = frame.operandStack
    stack.pushBoolean(klass.isInterface)
}

// public native boolean isPrimitive();
// ()Z
private fun isPrimitive(frame: KvmFrame) {
    val vars = frame.localVars
    val _this = vars.getRef(0u)!!
    val klass = _this.extraClass

    val stack = frame.operandStack
    stack.pushBoolean(klass.isPrimitive)
}

// private static native Class<?> forName0(String name, boolean initialize,
//                                         ClassLoader loader,
//                                         Class<?> caller)
//     throws ClassNotFoundException;
// (Ljava/lang/String;ZLjava/lang/ClassLoader;Ljava/lang/Class;)Ljava/lang/Class;
private fun forName0(frame: KvmFrame) {
    val vars = frame.localVars
    val jName = vars.getRef(0u)
    val initialize = vars.getBoolean(1u)
    //val jLoader = vars.getRef(2u)

    var ktName = kvmStrFromJStr(jName)!!
    ktName = ktName.replace(".", "/")
    val ktClass = frame.method.klass.loader.loadClass(ktName)
    val jClass = ktClass.jClass

    if (initialize && !ktClass.initStarted) {
        val thread = frame.thread
        frame.nextPC = thread.pc
        // init klass
        initClass(thread, ktClass)
    } else {
        val stack = frame.operandStack
        stack.pushRef(jClass)
    }
}

// public native int getModifiers();
// ()I
private fun getModifiers(frame: KvmFrame) {
    val vars = frame.localVars
    val _this = vars.getRef(0u)!!
    val klass = _this.extraClass
    val modifiers = klass.accessFlags

    val stack = frame.operandStack
    stack.pushInt(modifiers.toInt())
}

// public native Class<? super T> getSuperklass();
// ()Ljava/lang/Class;
private fun getSuperclass(frame: KvmFrame) {
    val vars = frame.localVars
    val _this = vars.getRef(0u)!!
    val klass = _this.extraClass
    val superClass = klass.superClass

    val stack = frame.operandStack
    if (superClass != null) {
        stack.pushRef(superClass.jClass)
    } else {
        stack.pushRef(null)
    }
}

// private native Class<?>[] getInterfaces0();
// ()[Ljava/lang/Class;
private fun getInterfaces0(frame: KvmFrame) {
    val vars = frame.localVars
    val _this = vars.getRef(0u)!!
    val klass = _this.extraClass
    val interfaces = klass.interfaces
    val klassArr = toClassArr(klass.loader, interfaces)

    val stack = frame.operandStack
    stack.pushRef(klassArr)
}

private fun toClassArr(loader: KvmClassLoader, classes: Array<KvmClass>?): KvmObject {
    val arrLen = classes?.size ?: 0

    val classArrClass = loader.loadClass("java/lang/Class").arrayClass
    val classArr = classArrClass.newArray(arrLen)

    val classObjs = classArr.refs
    classes?.forEachIndexed { i, clazz ->
        classObjs[i] = clazz.jClass
    }

    return classArr
}

// public native boolean isArray();
// ()Z
private fun isArray(frame: KvmFrame) {
    val vars = frame.localVars
    val _this = vars.getRef(0u)!!
    val klass = _this.extraClass
    val stack = frame.operandStack
    stack.pushBoolean(klass.isArray)
}

// public native Class<?> getComponentType();
// ()Ljava/lang/Class;
private fun getComponentType(frame: KvmFrame) {
    val vars = frame.localVars
    val _this = vars.getRef(0u)!!
    val klass = _this.extraClass
    val componentClass = klass.componentClass
    val componentClassObj = componentClass.jClass

    val stack = frame.operandStack
    stack.pushRef(componentClassObj)
}

// public native boolean isAssignableFrom(Class<?> cls);
// (Ljava/lang/Class;)Z
private fun isAssignableFrom(frame: KvmFrame) {
    val vars = frame.localVars
    val _this = vars.getRef(0u)!!
    val cls = vars.getRef(1u)!!

    val _thisClass = _this.extraClass
    val clsClass = cls.extraClass
    val ok = _thisClass.isAssignableFrom(clsClass)

    val stack = frame.operandStack
    stack.pushBoolean(ok)
}


private const val _constructorConstructorDescriptor =
    "(Ljava/lang/Class;" + "[Ljava/lang/Class;" + "[Ljava/lang/Class;" + "II" + "Ljava/lang/String;" + "[B[B)V"

private const val _fieldConstrutorDescriptor =
    "(Ljava/lang/Class;" + "Ljava/lang/String;" + "Ljava/lang/Class;" + "II" + "Ljava/lang/String;" + "[B)V"

private const val _methodConstructorDescriptor =
    "(Ljava/lang/Class;" + "Ljava/lang/String;" + "[Ljava/lang/Class;" + "Ljava/lang/Class;" + "[Ljava/lang/Class;" + "II" + "Ljava/lang/String;" + "[B[B[B))V"

private fun getDeclaredConstructors0(frame: KvmFrame) {
    val vars = frame.localVars
    val classObj = vars.getRef(0u)!!
    val publicOnly = vars.getBoolean(1u)

    val clazz = classObj.extraClass
    val constructors = clazz.getConstructors(publicOnly)
    val constructorCount = constructors.size

    val classLoader = frame.method.klass.loader
    val constructorClass = classLoader.loadClass("java/lang/reflect/Constructor")
    val constructorArr = constructorClass.arrayClass.newArray(constructorCount)

    val stack = frame.operandStack
    stack.pushRef(constructorArr)

    if (constructorCount > 0) {
        val thread = frame.thread
        val constructorObjs = constructorArr.refs
        val constructorInitMethod = constructorClass.getConstructor(_constructorConstructorDescriptor)

        constructors.forEachIndexed { idx, constructor ->
            val constructorObj = constructorClass.newObject()
            constructorObj.extraMethod = constructor
            constructorObjs[idx] = constructorObj

            val ops = KvmOperandStack(9u)
            ops.pushRef(constructorObj)
            ops.pushRef(classObj)
            ops.pushRef(toClassArr(classLoader, constructor.parameterTypes))
            ops.pushRef(toClassArr(classLoader, constructor.exceptionTypes))
            ops.pushInt(constructor.accessFlags.toInt())
            ops.pushInt(0)
            ops.pushRef(getSignatureStr(classLoader, constructor.signature))
            ops.pushRef(toByteArr(classLoader, constructor.annotationData))
            ops.pushRef(toByteArr(classLoader, constructor.parameterAnnotationData))

            val shimFrame = KvmShimFrame(thread, ops)
            thread.pushFrame(shimFrame)

            invokeMethod(shimFrame, constructorInitMethod)
        }
    }
}

fun toByteArr(loader: KvmClassLoader, bytes: ByteArray?): KvmObject? {
    if (bytes != null) {
        return newByteArray(loader, bytes)
    }
    return null
}

private fun getSignatureStr(loader: KvmClassLoader, signature: String?): KvmObject? {
    if (!signature.isNullOrEmpty()) {
        return kvmJString(loader, signature)
    }
    return null
}

private fun getDeclaredMethods0(frame: KvmFrame) {
    val vars = frame.localVars
    val classObj = vars.getRef(0u)!!
    val publicOnly = vars.getBoolean(1u)

    val klass = classObj.extraClass
    val methods = klass.getMethods(publicOnly)
    val methodCount = methods.size

    val classLoader = klass.loader
    val methodClass = classLoader.loadClass("java/lang/reflect/Method")
    val methodArr = methodClass.arrayClass.newArray(methodCount)

    val stack = frame.operandStack
    stack.pushRef(methodArr)

    if (methodCount > 0) {
        val thread = frame.thread
        val methodObjs = methodArr.refs
        val methodConstructor = methodClass.getConstructor(_methodConstructorDescriptor)
        methods.forEachIndexed { idx, method ->
            val methodObj = methodClass.newObject()
            methodObj.extraMethod = method
            methodObjs[idx] = methodObj

            val ops = KvmOperandStack(12u)
            // this
            ops.pushRef(methodObj)
            // 传入构造函数的参数
            ops.pushRef(classObj)
            ops.pushRef(kvmJString(classLoader, method.name))
            ops.pushRef(toClassArr(classLoader, method.parameterTypes))
            ops.pushRef(method.returnType.jClass)
            ops.pushRef(toClassArr(classLoader, method.exceptionTypes))
            ops.pushInt(method.accessFlags.toInt())
            ops.pushInt(0)
            ops.pushRef(getSignatureStr(classLoader, method.signature))
            ops.pushRef(toByteArr(classLoader, method.annotationData))
            ops.pushRef(toByteArr(classLoader, method.parameterAnnotationData))
            ops.pushRef(toByteArr(classLoader, method.annotationDefaultData))

            val shimFrame = KvmShimFrame(thread, ops)
            thread.pushFrame(shimFrame)

            // call constructor
            invokeMethod(shimFrame, methodConstructor)
        }
    }
}

private fun getDeclaredFields0(frame: KvmFrame) {
    val vars = frame.localVars
    val classObj = vars.getRef(0u)!!
    val publicOnly = vars.getBoolean(1u)

    val klass = classObj.extraClass
    val fields = klass.getFields(publicOnly)
    val fieldCount = fields.size

    val classLoader = frame.method.klass.loader
    val fieldClass = classLoader.loadClass("java/lang/reflect/Field")
    val fieldArr = fieldClass.arrayClass.newArray(fieldCount)

    val stack = frame.operandStack
    stack.pushRef(fieldArr)

    if (fieldCount > 0) {
        val thread = frame.thread
        val fieldObjs = fieldArr.refs
        val fieldConstructor = fieldClass.getConstructor(_fieldConstrutorDescriptor)
        fields.forEachIndexed { idx, field ->
            val fieldObj = fieldClass.newObject()
            fieldObj.extraField = field
            fieldObjs[idx] = fieldObj

            val ops = KvmOperandStack(8u)
            ops.pushRef(fieldObj)
            ops.pushRef(classObj)
            ops.pushRef(kvmJString(classLoader, field.name))
            ops.pushRef(field.type.jClass)
            ops.pushInt(field.accessFlags.toInt())
            ops.pushInt(field.slotId.toInt())
            ops.pushRef(getSignatureStr(classLoader, field.signature))
            ops.pushRef(toByteArr(classLoader, field.annotationData))

            val shimFrame = KvmShimFrame(thread, ops)
            thread.pushFrame(shimFrame)

            invokeMethod(shimFrame, fieldConstructor)
        }
    }
}