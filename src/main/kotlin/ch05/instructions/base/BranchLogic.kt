package ch05.instructions.base

import ch05.rtdata.KvmFrame

fun branch(frame: KvmFrame, offset: Int) {
    frame.nextPC = frame.thread.pc + offset
}