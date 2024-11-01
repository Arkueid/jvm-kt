package ch11

import ch11.classpath.Classpath
import ch11.rtdata.heap.KvmClassLoader

fun main(args: Array<String>) {
    val cmd = parseCmd(args)

    if (cmd.helpFlag) {
        printUsage()
    } else if (cmd.versionFlag) {
        println("version 0.0.1")
    } else if (cmd.klass == null) {
        printUsage()
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
        interpret(mainMethod, cmd.verboseInstFlag, cmd.jArgs)
    } else {
        throw RuntimeException("Main method not found in class ${cmd.klass}")
    }
}

