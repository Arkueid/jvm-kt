package ch11.rtdata

import ch11.rtdata.heap.KvmShimMethod

class KvmShimFrame(thread: KvmThread, operandStack: KvmOperandStack) :
    KvmFrame(thread, KvmShimMethod.returnMethod, operandStack) {
}