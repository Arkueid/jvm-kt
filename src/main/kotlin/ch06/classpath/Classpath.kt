package ch06.classpath

import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path

class Classpath {
    private var bootClassEntry: Entry? = null

    private var extClassEntry: Entry? = null
    private var userClasspathEntry: Entry? = null

    companion object {
        // 从命令行中解析 classpath
        @JvmStatic
        fun parse(jreOption: String, cpOption: String): Classpath {
            val cp = Classpath()
            cp.parseBootAndExtClasspath(jreOption)
            cp.parseUserClasspath(cpOption)

            return cp
        }
    }

    private fun parseBootAndExtClasspath(jreOption: String) {
        val jreDir = getJreDir(jreOption)

        val separator = File.separator
        // jre/lib/*
        val jreLibPath = "$jreDir${separator}lib$separator*"
        bootClassEntry = WildCardEntry(jreLibPath)

        // jre/lib/ext/*
        val jreExtPath = "$jreDir${separator}lib${separator}ext$separator*"
        extClassEntry = WildCardEntry(jreExtPath)
    }

    private fun getJreDir(jreOption: String): String {
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


    private fun parseUserClasspath(cpOption: String) {
        val cp = cpOption.ifEmpty { "." }
        userClasspathEntry = Entry.create(cp)
    }

    fun readClass(className: String): ReadClassResult {
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
}





