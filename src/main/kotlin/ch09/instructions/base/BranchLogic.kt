package ch09.instructions.base

import ch09.rtdata.KvmFrame

fun branch(frame: KvmFrame, offset: Int) {
    frame.nextPC = frame.thread.pc + offset
}