package ch11.instructions.constants

import ch11.rtdata.KvmFrame
import ch11.instructions.base.NoOperandsInstruction

class ACONST_NULL : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        frame.operandStack.pushRef(null)
    }

}

/**
 * dconst_0
 */
class DCONST_0 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        frame.operandStack.pushDouble(0.0)
    }

}

/**
 * dconst_1
 */
class DCONST_1 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        frame.operandStack.pushDouble(1.0)
    }

}

/**
 * fconst_0
 */
class FCONST_0 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        frame.operandStack.pushFloat(0.0f)
    }

}

/**
 * fconst_1
 */
class FCONST_1 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        frame.operandStack.pushFloat(1.0f)
    }

}

/**
 * fconst_2
 */
class FCONST_2 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        frame.operandStack.pushFloat(2.0f)
    }

}

/**
 * iconst_m1
 */
class ICONST_M1 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        frame.operandStack.pushInt(-1)
    }

}

/**
 * iconst_0
 */
class ICONST_0 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        frame.operandStack.pushInt(0)
    }

}

/**
 * iconst_1
 */
class ICONST_1 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        frame.operandStack.pushInt(1)
    }

}

/**
 * iconst_2
 */
class ICONST_2 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        frame.operandStack.pushInt(2)
    }

}

/**
 * iconst_3
 */
class ICONST_3 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        frame.operandStack.pushInt(3)
    }

}

/**
 * iconst_4
 */
class ICONST_4 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        frame.operandStack.pushInt(4)
    }
}

/**
 * iconst_5
 */
class ICONST_5 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        frame.operandStack.pushInt(5)
    }
}

/**
 * lconst_0
 */
class LCONST_0 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        frame.operandStack.pushLong(0)
    }
}

/**
 * lconst_1
 */
class LCONST_1 : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        frame.operandStack.pushLong(1)
    }
}
