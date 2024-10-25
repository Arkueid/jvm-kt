/**
 * 比较double
 */

package ch07.instructions.comparisons

import ch07.instructions.base.NoOperandsInstruction
import ch07.rtdata.KvmFrame

private fun dcmp(frame: KvmFrame, gFlag: Boolean) = frame.operandStack.run {
    val v2 = popDouble()
    val v1 = popDouble()
    if (v1 > v2) {
        pushInt(1)
    } else if (v1 == v2) {
        pushInt(0)
    } else if (v1 < v2) {
        pushInt(-1)
    } else if (gFlag) { // 两个比较的数任何一个是 NaN 导致无法比较时，根据 gFlag 返回不同的值
        pushInt(1)
    } else {
        pushInt(-1)
    }
}

class DCMPG : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = dcmp(frame, true)

}


class DCMPL : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = dcmp(frame, false)

}