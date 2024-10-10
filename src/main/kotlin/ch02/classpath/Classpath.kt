package ch02.classpath

import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path

class Classpath {
    var bootClassEntry: Entry? = null

    var extClassEntry: Entry? = null
    var userClasspathEntry: Entry? = null

    companion object

}

@JvmStatic
// 从命令行中解析 classpath
fun Classpath.Companion.parse(jreOption: String, cpOption: String): Classpath {
    val cp = Classpath()
    cp.parseBootAndExtClasspath(jreOption)
    cp.parseUserClasspath(cpOption)

    return cp
}

private fun Classpath.parseBootAndExtClasspath(jreOption: String) {
    val jreDir = getJreDir(jreOption)

    val separator = File.separator
    // jre/lib/*
    val jreLibPath = "$jreDir${separator}lib$separator*"
    bootClassEntry = WildCardEntry(jreLibPath)

    // jre/lib/ext/*
    val jreExtPath = "$jreDir${separator}lib${separator}ext$separator*"
    extClassEntry = WildCardEntry(jreExtPath)
}

fun Classpath.getJreDir(jreOption: String): String {
    if (jreOption.isNotEmpty() && Files.exists(Path(jreOption))) {
        return jreOption
    }

    if (Files.exists(Path("./jre"))) {
        return "./jre"
    }

    System.getenv("JAVA_HOME")?.let {
        if (it.isNotEmpty()) {
            return "$it${File.separator}jre"
        }
    }

    throw RuntimeException("Can not find jre folder")
}


private fun Classpath.parseUserClasspath(cpOption: String) {
    val cp = if (cpOption.isEmpty()) {
        "."
    } else {
        cpOption
    }
    userClasspathEntry = Entry.create(cp)
}

fun Classpath.readClass(className: String): ReadClassResult {
    val classNameWithExt = "${className}.class"

    bootClassEntry!!.readClass(classNameWithExt).let {
        if (it.error == null) {
            return it
        }
    }

    extClassEntry!!.readClass(classNameWithExt).let {
        if (it.error == null) {
            return it
        }
    }

    return userClasspathEntry!!.readClass(classNameWithExt)
}

fun Classpath.String(): String = userClasspathEntry!!.String()

