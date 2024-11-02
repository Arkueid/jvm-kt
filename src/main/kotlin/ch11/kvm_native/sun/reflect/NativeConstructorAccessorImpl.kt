package ch11.kvm_native.sun.reflect

import ch11.instructions.base.initClass
import ch11.instructions.base.invokeMethod
import ch11.kvm_native.KvmNative
import ch11.rtdata.KvmFrame
import ch11.rtdata.KvmOperandStack
import ch11.rtdata.KvmShimFrame
import ch11.rtdata.heap.KvmMethod
import ch11.rtdata.heap.KvmObject

object NativeConstructorAccessorImpl {
    fun init() =
        KvmNative.register(
            "sun/reflect/NativeConstructorAccessorImpl",
            "newInstance0",
            "(Ljava/lang/reflect/Constructor;[Ljava/lang/Object;)Ljava/lang/Object;",
            ::newInstance0
        )
}

private fun newInstance0(frame: KvmFrame) {
    val vars = frame.localVars
    val constructorObj = vars.getRef(0u)!!
    val argArrObj = vars.getRef(1u)

    val kvmConstructor = getKvmConstructor(constructorObj)!!
    val klass = kvmConstructor.klass
    if (!klass.initStarted) {
        frame.revertNextPC()
        initClass(frame.thread, klass)
        return
    }

    val obj = klass.newObject()
    val stack = frame.operandStack
    stack.pushRef(obj)

    // call <init>
    val ops = convertArgs(obj, argArrObj, kvmConstructor)
    val shimFrame = KvmShimFrame(frame.thread, ops)
    frame.thread.pushFrame(shimFrame)

    invokeMethod(shimFrame, kvmConstructor)

}

fun getKvmConstructor(obj: KvmObject): KvmMethod? {
    return _getKvmMethod(obj, true)
}

fun getKvmMethod(methodObj: KvmObject): KvmMethod? {
    return _getKvmMethod(methodObj, false)
}

fun _getKvmMethod(obj: KvmObject, isConstructor: Boolean): KvmMethod? {
    val extra = obj.extraMethod
    if (extra != null) {
        return extra
    }

    if (isConstructor) {
        val root = obj.getRefVar("root", "Ljava/lang/reflect/Constructor;")
        return root.extraMethod
    } else {
        val root = obj.getRefVar("root", "Ljava/lang/reflect/Method;")
        return root.extraMethod
    }
}


fun convertArgs(_this: KvmObject, argArr: KvmObject?, method: KvmMethod): KvmOperandStack {
    if (method.argSlotCount == 0) {
        return KvmOperandStack(0u)
    }

    // TODO

    val ops = KvmOperandStack(method.argSlotCount.toUInt())
    if (!method.isStatic) {
        ops.pushRef(_this)
    }

    if (method.argSlotCount == 1 && !method.isStatic) {
        return ops
    }

    // TODO

    return ops

}
