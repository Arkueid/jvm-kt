package ch11.kvm_native.java.lang

import ch11.kvm_native.KvmNative
import ch11.rtdata.KvmFrame
import ch11.rtdata.heap.KvmClass
import ch11.rtdata.heap.kvmJString
import ch11.rtdata.heap.kvmStrFromJStr
import ch11.rtdata.heap.newByteArray
import ch11.showFrame
import ch11.showStack
import java.io.File
import java.lang.System

object ClassLoader {
    fun init() {
        KvmNative.register(
            "java/lang/ClassLoader",
            "findBuiltinLib",
            "(Ljava/lang/String;)Ljava/lang/String;",
            ::findBuiltinLib
        )

        KvmNative.register(
            "java/lang/ClassLoader\$NativeLibrary",
            "load",
            "(Ljava/lang/String;ZZ)V",
            ::load
        )
    }
}

private fun findBuiltinLib(frame: KvmFrame) {
    val jStr = frame.localVars.getRef(0u)
    val kStr = kvmStrFromJStr(jStr)
    // TODO
    if (kStr == "zip.dll") {
        val absolutePath = kvmJString(
            frame.method.klass.loader,
            File("D:/jdk/jdk-17.0.9/../jdk1.8.0_431/jre/bin/zip.dll").absolutePath
        )
        frame.operandStack.pushRef(absolutePath)
        return
    }
    frame.operandStack.pushRef(null)
}

private fun load(frame: KvmFrame) {
    val name = frame.localVars.getRef(1u)
    val path = kvmStrFromJStr(name)!!

    val nativeLibObj = frame.localVars.getRef(0u)!!

    // 看看加载过程可能会修改哪些字段
//    var clazz: KvmClass? = nativeLibObj.klass
//    while (clazz != null) {
//        clazz.fields.forEach { println(clazz.name + "." + it.name + it.descriptor) }
//        clazz = clazz.superClass
//    }

    nativeLibObj.setIntVar("loaded", "Z", 1)

//    println("loaded: $path")
}