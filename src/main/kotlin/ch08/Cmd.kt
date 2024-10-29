package ch08

import com.beust.jcommander.JCommander
import com.beust.jcommander.Parameter

class Cmd {
    @Parameter(names = ["-help", "-h", "?"], description = "print usage")
    var helpFlag: Boolean = false

    @Parameter(names = ["-version", "-v"], description = "print version and exit")
    var versionFlag: Boolean = false

    @Parameter(names = ["-cpOptions", "-cp"], description = "classpath")
    var cpOptions: String = ""

    @Parameter(names = ["-Xjre"], description = "path to jre")
    var XjreOption: String = ""

    @Parameter(names = ["-verboseClass"], description = "print class info during execution")
    var verboseClassFlag: Boolean = false

    @Parameter(names = ["-verboseInst"], description = "print inst info during execution")
    var verboseInstFlag: Boolean = false

    var klass: String? = null

    @Parameter(description = "extra arguments")
    var args: MutableList<String> = mutableListOf()
}


fun parseCmd(args: Array<String>): Cmd {
    val cmd = Cmd()
    JCommander.newBuilder().addObject(cmd).build().parse(*args)

    if (cmd.args.isNotEmpty()) {
        cmd.klass = cmd.args[0]
        cmd.args = cmd.args.apply { removeFirst() }
    }

    return cmd
}

fun printUsage(arg0: String) {
    println("Usage: $arg0 [--options] class [args...]")
}