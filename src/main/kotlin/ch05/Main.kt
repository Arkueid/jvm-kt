package ch05

import ch05.classpath.Classpath
import ch05.classfile.ClassFile

fun main(args: Array<String>) {
    val cmd = parseCmd(args)

    if (cmd.helpFlag) {
        printUsage(args[0])
    } else if (cmd.versionFlag) {
        println("version 0.0.1")
    } else {
        startJvm(cmd)
    }
}

fun startJvm(cmd: Cmd) {
    val cp = Classpath.parse(cmd.XjreOption, cmd.cpOptions)

    val className = cmd.`class`!!.replace(".", "/")
    val cf = loadClass(className, cp)
    println(cmd.`class`)
    printClassInfo(cf)
}

fun loadClass(className: String, cp: Classpath): ClassFile {
    val cpResult = cp.readClass(className)
    if (cpResult.error != null) {
        throw RuntimeException(cpResult.error)
    }

    val cfResult = ClassFile.parse(cpResult.data!!)
    if (cfResult.error != null) {
        throw RuntimeException(cfResult.error)
    }

    return cfResult.classFile!!
}

@OptIn(ExperimentalStdlibApi::class)
fun printClassInfo(cf: ClassFile) {
    println("version: ${cf.majorVersion}.${cf.minorVersion}")
    println("constants count: ${cf.constantPool!!.size}")
    println("access flags: 0x${cf.accessFlags.toHexString()}")
    println("this class: ${cf.className}")
    println("super class: ${cf.superClassName}")
    println("interfaces: ${cf.interfaceNames.joinToString(", ", prefix = "[", postfix = "]")}")
    println("fields count: ${cf.fields!!.size}")

    cf.fields!!.forEach {
        println("   ${it.name}")
    }

    println("methods count: ${cf.methods!!.size}")
    cf.methods!!.forEach {
        println("   ${it.name}")
    }
}
