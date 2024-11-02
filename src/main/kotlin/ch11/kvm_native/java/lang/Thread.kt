package ch11.kvm_native.java.lang

import ch11.kvm_native.KvmNative
import ch11.rtdata.KvmFrame

object Thread {
    fun init() {
        KvmNative.register("java/lang/Thread", "currentThread", "()Ljava/lang/Thread;", ::currentThread)
        KvmNative.register("java/lang/Thread", "setPriority0", "(I)V", ::setPriority0)
        KvmNative.register("java/lang/Thread", "isAlive", "()Z", ::isAlive)
        KvmNative.register("java/lang/Thread", "start0", "()V", ::start0)
    }
}

private fun currentThread(frame: KvmFrame) {
    val classLoader = frame.method.klass.loader
    val threadClass = classLoader.loadClass("java/lang/Thread")
    val jThread = threadClass.newObject()

    val threadGroupClass = classLoader.loadClass("java/lang/ThreadGroup")
    val jGroup = threadGroupClass.newObject()

    jThread.setRefVar("group", "Ljava/lang/ThreadGroup;", jGroup)
    jThread.setIntVar("priority", "I", 1)

    frame.operandStack.pushRef(jThread)
}

private fun setPriority0(frame: KvmFrame) {
    // TODO
}

private fun isAlive(frame: KvmFrame) {
    frame.operandStack.pushBoolean(false)
}

private fun start0(frame: KvmFrame) {
    // TODO
}