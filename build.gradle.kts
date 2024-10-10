import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    kotlin("jvm") version "2.0.10"
}

group = "com.arkueid"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly("org.jcommander:jcommander:2.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "ch01.MainKt"
    }

    // 排除掉重复文件
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    // 将依赖一并打包如 jar 中
    configurations["compileClasspath"].forEach {file: File->
        from(zipTree(file.absoluteFile))
    }
}