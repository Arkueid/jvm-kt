<div align="center">
    <h1>jvm-kt</h1>
</div>

---

参照[《自己动手写Java虚拟机》](https://github.com/zxh0/jvmgo-book)实现的 Kotlin 版套娃。

每一章代码分别在 `src\main\kotlin\ch0X` 目录下。

## 运行

环境：

* Kotlin 运行环境为 Windows + JDK17
* 所实现的虚拟机使用 JDK1.8

运行某一章的代码，首先修改 `build.gradle.kts` 中的章节目录：

```kotlin
tasks.jar {
    val currentChapter = "ch0X"

    // 其他代码
}
```

使用 `Gradle` 构建 `JAR`包。

命令行中启动虚拟机

```commandline
aoyama7mi-jvm.bat 参数 [-选项...]
```

执行某一章的测试，则在项目根目录执行以下命令：

```commandline
run-test.bat ch0X
```

某一章的测试可能有多个测试用例，对其进行切换：

首先，修改 `src\test\kotlin\ch0X\run-test.bat` 文件，例如第8章：

将想要测试的一行取消注释`@REM`

```bat
cls

@REM 测试气泡排序
@REM aoyama7mi-jvm.bat -Xjre %JAVA_HOME%\..\jdk1.8.0_431\jre src.test.kotlin.ch08.BubbleSortTest -verboseClass true

@REM 测试打印字符串
@REM aoyama7mi-jvm.bat -Xjre %JAVA_HOME%\..\jdk1.8.0_431\jre src.test.kotlin.ch08.HelloWorld -verboseClass wow sljdals sjalsl
```

## 主要参考
* [《自己动手写Java虚拟机》](https://github.com/zxh0/jvmgo-book)
* [openjdk/tag:jdk8-b77](https://github.com/openjdk/jdk/tree/jdk8-b77)