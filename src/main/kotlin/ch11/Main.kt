package ch11

fun main(args: Array<String>) {
    val cmd = parseCmd(args)

    if (cmd.helpFlag) {
        printUsage()
    } else if (cmd.versionFlag) {
        println("version 0.0.1")
    } else if (cmd.klass == null) {
        printUsage()
    } else {
        Kvm(cmd).start()
    }
}


