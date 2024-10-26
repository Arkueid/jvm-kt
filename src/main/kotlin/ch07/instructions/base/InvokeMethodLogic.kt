package ch07.instructions.base

import ch07.rtdata.KvmFrame
import ch07.rtdata.heap.KvmMethod

fun invokeMethod(frame: KvmFrame, method: KvmMethod) {
    val thread = frame.thread
    val newFrame = thread.newFrame(method)
    thread.pushFrame(newFrame)

    // 操作数个数
    var slotId = method.argSlotCount - 1
    while (slotId >= 0) {
        val slot = frame.operandStack.popSlot()
        newFrame.localVars.setSlot(slotId.toUInt(), slot)
        slotId--
    }

    // 暂时跳过本地方法
    if (method.isNative) {
        if (method.name == "registerNatives") {
            thread.popFrame()
        } else {
            throw RuntimeException("native method: ${method.klass.name}.${method.name}${method.descriptor}")
        }
    }
}