package ch08.instructions.base

import ch08.rtdata.KvmFrame

fun branch(frame: KvmFrame, offset: Int) {
    frame.nextPC = frame.thread.pc + offset
}