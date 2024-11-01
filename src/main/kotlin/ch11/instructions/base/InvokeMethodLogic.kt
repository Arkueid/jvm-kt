package ch11.instructions.base

import ch11.rtdata.KvmFrame
import ch11.rtdata.heap.KvmMethod

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