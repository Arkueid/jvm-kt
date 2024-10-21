package ch06.instructions.base

import ch06.rtdata.KvmFrame

fun branch(frame: KvmFrame, offset: Int) {
    frame.nextPC = frame.thread.pc + offset
}