package ch08

import ch08.classpath.Classpath
import ch08.rtdata.heap.KvmClassLoader

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

    val classLoader = KvmClassLoader(cp, cmd.verboseClassFlag)
    val className = cmd.klass!!.replace(".", "/")
    val mainClass = classLoader.loadClass(className)
    val mainMethod = mainClass.mainMethod

    if (mainMethod != null) {
        interpret(mainMethod, cmd.verboseInstFlag)
    } else {
        throw RuntimeException("Main method not found in class ${cmd.klass}")
    }
}

