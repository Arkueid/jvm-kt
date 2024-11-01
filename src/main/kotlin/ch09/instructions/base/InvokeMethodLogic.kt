package ch09.instructions.base

import ch09.rtdata.KvmFrame
import ch09.rtdata.heap.KvmMethod

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
}