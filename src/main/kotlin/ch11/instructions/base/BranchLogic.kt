package ch11.instructions.base

import ch11.rtdata.KvmFrame

fun branch(frame: KvmFrame, offset: Int) {
    frame.nextPC = frame.thread.pc + offset
}