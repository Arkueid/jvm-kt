package ch11

import ch11.classpath.Classpath
import ch11.instructions.base.BytecodeReader
import ch11.instructions.base.Instruction
import ch11.instructions.base.initClass
import ch11.rtdata.KvmFrame
import ch11.rtdata.KvmThread
import ch11.rtdata.heap.KvmClassLoader
import ch11.rtdata.heap.KvmObject
import ch11.rtdata.heap.kvmJString
import ch11.rtdata.heap.newArray
import ch11.rtdata.heap.refs

class Kvm(
    private val cmd: Cmd,
) {
    private var classLoader: KvmClassLoader
    private var mainThread: KvmThread

    init {
        val cp = Classpath.parse(cmd.XjreOption, cmd.cpOptions)
        classLoader = KvmClassLoader(cp, cmd.verboseClassFlag)
        mainThread = KvmThread()
    }

    fun start() {
        initVM()
        execMain()
    }

    private fun initVM() {
        val vmClass = classLoader.loadClass("sun/misc/VM")
        initClass(mainThread, vmClass)
        interpret(mainThread, cmd.verboseInstFlag)
    }

    private fun execMain() {
        val className = cmd.klass!!.replace(".", "/")
        val mainClass = classLoader.loadClass(className)
        val mainMethod = mainClass.mainMethod
        if (mainMethod == null) {
            println("Main method not found in class ${cmd.klass}")
            return
        }

        val argsArr = createArgsArray()
        val frame = mainThread.newFrame(mainMethod)
        frame.localVars.setRef(0u, argsArr)
        mainThread.pushFrame(frame)
        interpret(mainThread, cmd.verboseInstFlag)
    }

    private fun interpret(thread: KvmThread, verboseInstFlag: Boolean) {
        try {
            loop(thread, verboseInstFlag, cmd.stackDebug)
        } catch (e: Exception) {
            handleErr(e, thread)
        }
    }

    private fun createArgsArray(): KvmObject {
        val strings = cmd.jArgs
        val stringClass = classLoader.loadClass("java/lang/String")
        val jStrArray = stringClass.arrayClass.newArray(strings.size)
        val refs = jStrArray.refs
        strings.forEachIndexed { index, ktStr ->
            refs[index] = kvmJString(classLoader, ktStr)
        }
        return jStrArray
    }
}


private fun loop(thread: KvmThread, logInst: Boolean, showStack: Boolean = false) {
    val reader = BytecodeReader()
    while (true) {
        val frame = thread.currentFrame
        val pc = frame.nextPC
        thread.pc = pc

        reader.reset(frame.method.code, pc)
        val opcode = reader.readUint8()
        val inst = Instruction.create(opcode)
        inst.fetchOperands(reader)
        frame.nextPC = reader.pc

        if (logInst) {
            logInstruction(frame, inst)
        }

        inst.execute(frame)

        if (thread.isStackEmpty) {
            break
        }

        // debug
        if (showStack) {
            showStack(frame)
            println()
        }
    }
}

/**
 * 打印指令
 */
private fun logInstruction(frame: KvmFrame, inst: Instruction) {
    val method = frame.method
    val className = method.klass.name
    val methodName = method.name
    println(String.format("${className}.$methodName() %2d ${inst.javaClass.simpleName}", frame.thread.pc))
}

/**
 * 处理执行异常
 */
private fun handleErr(e: Exception, thread: KvmThread) {
    showFrames(thread)
    throw RuntimeException(e)
}

/**
 * 打印调用栈
 */
fun showFrames(thread: KvmThread) {
    while (!thread.isStackEmpty) {
        showFrame(thread.popFrame())
    }
}

/**
 * 打印单个栈帧
 */
fun showFrame(frame: KvmFrame) {
    val method = frame.method
    val className = method.klass.name
    try {
        println(">>${method.getLineNumber(frame.nextPC)} pc: ${frame.nextPC} $className.${method.name}${method.descriptor}")
    } catch (_: Exception) {
        println(">>${method.getLineNumber(frame.nextPC)} pc: ${frame.nextPC} $className.${method.name}")
    }
}

/**
 * 执行过程中显示栈内容
 */
fun showStack(frame: KvmFrame) {
    println("$frame".let { it.substring(it.indexOfFirst { it == '@' }) })
    val localVarsString = frame.localVars.slots.joinToString(
        prefix = "[",
        postfix = "]",
        separator = ", "
    ) { "(num=${it.num}, ref=${it.ref})" }
    println("Local Vars: $localVarsString")
    val operandStackString = frame.operandStack.slots.joinToString(
        prefix = "[",
        postfix = "]",
        separator = ", "
    ) { "(num=${it.num}, ref=${it.ref})" }
    println("Operand Stack: $operandStackString")
}
