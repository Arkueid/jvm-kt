package ch10.kvm_native.java.lang

import ch10.kvm_native.KvmNative
import ch10.rtdata.KvmFrame
import ch10.rtdata.KvmThread
import ch10.rtdata.heap.KvmClass
import ch10.rtdata.heap.KvmObject
import kotlin.String

object Throwable {
    @JvmStatic
    fun init() =
        KvmNative.register("java/lang/Throwable", "fillInStackTrace", "(I)Ljava/lang/Throwable;", ::fillInStackTrace)
}

// private native Throwable fillInStackTrace(int dummy)
private fun fillInStackTrace(frame: KvmFrame) {
    val _this = frame.localVars.getRef(0u)!!
    frame.operandStack.pushRef(_this)

    val stes = createStackTraceElements(_this, frame.thread)
    _this.exceptionInfo = stes
}

private fun createStackTraceElements(tObj: KvmObject, thread: KvmThread): Array<KvmStackTraceElement> {
    // 跳过正在执行的异常构造方法嵌套，回到真正出现异常的位置
    // 所有的嵌套包括：
    //  异常的构造方法从 java.lang.Object 开始
    //  再加上 两个调用 fillInStackTrace() -> fillInStackTrace(0)
    val skip = distanceToObject(tObj.klass) + 2
    val frames = thread.frames.let { it.slice(skip..it.size - 1) }
    return Array<KvmStackTraceElement>(frames.size) {
        createStackTraceElement(frames[it])
    }
}

// 到 java/lang/Object 的距离
fun distanceToObject(klass: KvmClass): Int {
    var distance = 0
    var clazz = klass.superClass
    while (clazz != null) {
        distance++
        clazz = clazz.superClass
    }
    return distance
}

fun createStackTraceElement(frame: KvmFrame): KvmStackTraceElement {
    val method = frame.method
    val clazz = method.klass
    return KvmStackTraceElement(
        clazz.sourceFile,
        clazz.javaName,
        method.name,
        method.getLineNumber(frame.nextPC - 1)
    )
}

class KvmStackTraceElement(
    val fileName: String,
    val className: String,
    val methodName: String,
    val lineNumber: Int,
) {
    fun String(): String = "$className.$methodName($fileName:$lineNumber)"
}

