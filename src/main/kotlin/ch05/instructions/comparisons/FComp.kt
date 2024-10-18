/**
 * 比较浮点数
 */

package ch05.instructions.comparisons

import ch05.instructions.base.NoOperandsInstruction
import ch05.rtdata.KvmFrame

private fun fcmp(frame: KvmFrame, gFlag: Boolean) = frame.operandStack.run {
    val v2 = popFloat()
    val v1 = popFloat()
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

class FCMPG : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = fcmp(frame, true)

}


class FCMPL : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) = fcmp(frame, false)

}