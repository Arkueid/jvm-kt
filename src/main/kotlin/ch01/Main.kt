package ch01

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
    println("classpath: ${cmd.cpOptions}, class: ${cmd.`class`}, args: ${cmd.args?.joinToString(", ", prefix = "[", postfix = "]")}")
}
