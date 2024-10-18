package ch05.instructions.base

import ch05.rtdata.KvmFrame

fun branch(frame: KvmFrame, offset: Int) {
    val currentPC = frame.thread.pc
    frame.setNextPC(currentPC + offset)
}