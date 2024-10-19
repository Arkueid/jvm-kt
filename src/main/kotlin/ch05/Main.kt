package ch05

import ch05.classpath.Classpath
import ch05.classfile.ClassFile
import ch05.classfile.MemberInfo

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

    val mainMethod = getMainMethod(cf)
    if (mainMethod != null) {
        interpret(mainMethod)
    } else {
        throw RuntimeException("Main method not found in class ${cmd.`class`}")
    }
}

fun getMainMethod(classFile: ClassFile): MemberInfo? {
    for (method in classFile.methods) {
        if (method.name == "main" && method.descriptor == "([Ljava/lang/String;)V") {
            return method
        }
    }
    return null
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