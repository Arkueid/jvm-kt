package ch11.instructions.references

import ch11.instructions.base.Index16Instruction
import ch11.instructions.base.initClass
import ch11.instructions.base.invokeMethod
import ch11.rtdata.KvmFrame
import ch11.rtdata.heap.KvmMethodRef

class INVOKE_STATIC : Index16Instruction() {
    override fun execute(frame: KvmFrame) {
        val cp = frame.method.klass.constantPool
        val methodRef = cp.getConstant(index) as KvmMethodRef
        val method = methodRef.resolvedMethod
        if (!method.isStatic) {
            // 静态方法中，类初始化方法只能由虚拟机调用
            // 类初始化方法<clinit> vs 初始化方法<init>
            // 类初始化方法在类的数据被加载到内存的过程中执行，整个程序周期中只执行一次（一般？
            // 初始化方法是实例化的时候执行
            throw RuntimeException("java.lang.IncompatibleClassChangeError")
        }

        val klass = method.klass
        if (!klass.initStarted) {
            frame.revertNextPC()
            initClass(frame.thread, klass)
            return
        }

        invokeMethod(frame, method)
    }

}