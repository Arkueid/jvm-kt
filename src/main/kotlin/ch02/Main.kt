package ch02

import ch02.classpath.Classpath

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
    println(
        "classpath: ${cp.String()} " +
                "class: ${cmd.`class`} " +
                "args: ${cmd.args.joinToString(", ", prefix = "[", postfix = "]")}"
    )

    val className = cmd.`class`!!.replace(".", "/")
    val result = cp.readClass(className)

    if (result.error != null) {
        println("Could not find or load main class ${cmd.`class`}")
        return throw RuntimeException(result.error)
    }

    println("class data: ${result.data?.joinToString()}")
}
