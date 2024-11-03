package ch11.kvm_native.java.lang

import ch11.instructions.base.invokeMethod
import ch11.kvm_native.KvmNative
import ch11.rtdata.KvmFrame
import ch11.rtdata.KvmOperandStack
import ch11.rtdata.KvmShimFrame
import ch11.rtdata.heap.KvmObject
import ch11.rtdata.heap.arrayLength
import ch11.rtdata.heap.componentClass
import ch11.rtdata.heap.isArray
import ch11.rtdata.heap.kvmArrayCopy
import ch11.rtdata.heap.kvmJString
import ch11.rtdata.heap.kvmStrFromJStr
import java.lang.System
import kotlin.String

object System {
    @JvmStatic
    fun init() {
        KvmNative.register("java/lang/System", "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V", ::arraycopy)
        KvmNative.register(
            "java/lang/System",
            "initProperties",
            "(Ljava/util/Properties;)Ljava/util/Properties;",
            ::initProperties
        )

        KvmNative.register("java/lang/System", "setIn0", "(Ljava/io/InputStream;)V", ::setIn0)
        KvmNative.register("java/lang/System", "setOut0", "(Ljava/io/PrintStream;)V", ::setOut0)
        KvmNative.register("java/lang/System", "setErr0", "(Ljava/io/PrintStream;)V", ::setErr0)
        KvmNative.register("java/lang/System", "currentTimeMillis", "()J", ::currentMillis)

        KvmNative.register(
            "java/lang/System",
            "mapLibraryName",
            "(Ljava/lang/String;)Ljava/lang/String;",
            ::mapLibraryName
        )
    }

}

// public static native void arraycopy(
// Object src, int srcPos, Object dst, int dstPos, int length
// )
private fun arraycopy(frame: KvmFrame) {
    val vars = frame.localVars
    val src = vars.getRef(0u)
    val srcPos = vars.getInt(1u)
    val dst = vars.getRef(2u)
    val dstPos = vars.getInt(3u)
    val length = vars.getInt(4u)

    if (src == null || dst == null) {
        throw RuntimeException("java.lang.NullPointerException")
    }

    if (!checkArrayCopy(src, dst)) {
        throw RuntimeException("java.lang.ArrayStoreException")
    }

    if (srcPos < 0 || dstPos < 0 || length < 0 ||
        srcPos + length > src.arrayLength ||
        dstPos + length > dst.arrayLength
    ) {
        throw RuntimeException("java.lang.IndexOutOfBoundsException")
    }

    kvmArrayCopy(src, srcPos, dst, dstPos, length)
}

private fun checkArrayCopy(src: KvmObject, dst: KvmObject): Boolean {
    val srcClass = src.klass
    val dstClass = dst.klass

    if (!srcClass.isArray || !dstClass.isArray) {
        return false
    }

    if (srcClass.componentClass.isPrimitive || dstClass.isPrimitive) {
        return dstClass == srcClass
    }

    return true
}


private fun initProperties(frame: KvmFrame) {
    val vars = frame.localVars
    val props = vars.getRef(0u)!!

    val stack = frame.operandStack
    stack.pushRef(props)

    val setPropMethod =
        props.klass.getInstanceMethod("setProperty", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;")!!
    val thread = frame.thread
    for ((k, v) in _sysProps) {
        val jKey = kvmJString(frame.method.klass.loader, k)
        val jVal = kvmJString(frame.method.klass.loader, v)
        val ops = KvmOperandStack(3u)
        ops.pushRef(props)
        ops.pushRef(jKey)
        ops.pushRef(jVal)
        val frame = KvmShimFrame(thread, ops)
        thread.pushFrame(frame)

        invokeMethod(frame, setPropMethod)
    }
}

private val _sysProps = mutableMapOf<String, String>(
    Pair("java.version", "1.8.0"),
    Pair("java.vendor", "jvm.go"),
    Pair("java.vendor.url", "https://github.com/Arkueid/jvm-kt"),
    Pair("java.home", ""),
    Pair("sun.boot.library.path", ""),
    Pair("java.library.path", ""),
    Pair("java.class.version", "52.0"),
    Pair("java.class.path", "todo"),
    Pair("java.awt.graphicsenv", "sun.awt.CGraphicsEnvironment"),
    Pair("os.name", System.getProperty("os.name")),
    Pair("os.arch", System.getProperty("os.arch")),
    Pair("os.version", ""),             // todo
    Pair("file.separator", "/"),            // todo os.PathSeparator
    Pair("path.separator", ":"),            // todo os.PathListSeparator
    Pair("line.separator", "\n"),           // todo
    Pair("user.name", ""),             // todo
    Pair("user.home", ""),             // todo
    Pair("user.dir", "."),            // todo
    Pair("user.country", "CN"),           // todo
    Pair("file.encoding", "UTF-8"),
    Pair("sun.stdout.encoding", "UTF-8"),
    Pair("sun.stderr.encoding", "UTF-8"),
)


private fun setIn0(frame: KvmFrame) {
    val ins = frame.localVars.getRef(0u)!!
    val sysClass = frame.method.klass
    sysClass.setRefVar("in", "Ljava/io/InputStream;", ins)
}


// private static native void setOut0(PrintStream out);
private fun setOut0(frame: KvmFrame) {
    val out = frame.localVars.getRef(0u)!!
    val sysClass = frame.method.klass
    sysClass.setRefVar("out", "Ljava/io/PrintStream;", out)
}

private fun setErr0(frame: KvmFrame) {
    val err = frame.localVars.getRef(0u)!!
    val sysClass = frame.method.klass
    sysClass.setRefVar("err", "Ljava/io/PrintStream;", err)
}

private fun currentMillis(frame: KvmFrame) {
    val millis = System.currentTimeMillis()
    val stack = frame.operandStack
    stack.pushLong(millis)
}

private fun mapLibraryName(frame: KvmFrame) {
    val jStr = frame.localVars.getRef(0u)
    val kStr = kvmStrFromJStr(jStr)
    val kLibName = System.mapLibraryName(kStr)
    frame.operandStack.pushRef(kvmJString(frame.method.klass.loader, kLibName))
}