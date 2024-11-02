package ch11.instructions.base

import ch11.rtdata.KvmThread
import ch11.rtdata.heap.KvmClass

fun initClass(thread: KvmThread, klass: KvmClass) {
    // 切换状态值
    klass.startInit()
    // 将本类的初始化函数入栈，等待执行
    scheduleClinit(thread, klass)
    // 初始化超类
    initSuperClass(thread, klass)
}

private fun initSuperClass(thread: KvmThread, klass: KvmClass) {
    if (!klass.isInterface) {
        val superClass = klass.superClass
        superClass?.let {
            if (!it.initStarted) {
                initClass(thread, superClass)
            }
        }
    }
}

private fun scheduleClinit(thread: KvmThread, klass: KvmClass) {
    val clinit = klass.clinitMethod
    if (clinit != null && clinit.klass == klass) {
        // exec <clinit>
        val newFrame = thread.newFrame(clinit)
        thread.pushFrame(newFrame)
    }
}

