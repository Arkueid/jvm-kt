package ch11.instructions.loads

import ch11.instructions.base.NoOperandsInstruction
import ch11.rtdata.KvmFrame
import ch11.rtdata.heap.bytes
import ch11.rtdata.heap.chars
import ch11.rtdata.heap.doubles
import ch11.rtdata.heap.floats
import ch11.rtdata.heap.ints
import ch11.rtdata.heap.longs
import ch11.rtdata.heap.refs
import ch11.rtdata.heap.shorts

class AALOAD : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val index = stack.popInt()
        val arrRef = stack.popRef()

        if (arrRef == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }

        val refs = arrRef.refs
        if (index < 0 || index >= refs.size) {
            throw RuntimeException("ArrayIndexOutOfBoundsException")
        }
        stack.pushRef(refs[index])
    }

}

class BALOAD : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val index = stack.popInt()
        val arrRef = stack.popRef()

        if (arrRef == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }

        val refs = arrRef.bytes
        if (index < 0 || index >= refs.size) {
            throw RuntimeException("ArrayIndexOutOfBoundsException")
        }
        stack.pushInt(refs[index].toInt())
    }

}

class CALOAD : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val index = stack.popInt()
        val arrRef = stack.popRef()

        if (arrRef == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }

        val refs = arrRef.chars
        if (index < 0 || index >= refs.size) {
            throw RuntimeException("ArrayIndexOutOfBoundsException")
        }
        stack.pushInt(refs[index].toInt())
    }

}

class DALOAD : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val index = stack.popInt()
        val arrRef = stack.popRef()

        if (arrRef == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }

        val refs = arrRef.doubles
        if (index < 0 || index >= refs.size) {
            throw RuntimeException("ArrayIndexOutOfBoundsException")
        }
        stack.pushDouble(refs[index])
    }

}

class FALOAD : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val index = stack.popInt()
        val arrRef = stack.popRef()

        if (arrRef == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }

        val refs = arrRef.floats
        if (index < 0 || index >= refs.size) {
            throw RuntimeException("ArrayIndexOutOfBoundsException")
        }
        stack.pushFloat(refs[index])
    }

}

class IALOAD : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val index = stack.popInt()
        val arrRef = stack.popRef()

        if (arrRef == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }

        val refs = arrRef.ints
        if (index < 0 || index >= refs.size) {
            throw RuntimeException("ArrayIndexOutOfBoundsException")
        }
        stack.pushInt(refs[index])
    }

}

class LALOAD : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val index = stack.popInt()
        val arrRef = stack.popRef()

        if (arrRef == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }

        val refs = arrRef.longs
        if (index < 0 || index >= refs.size) {
            throw RuntimeException("ArrayIndexOutOfBoundsException")
        }
        stack.pushLong(refs[index])
    }

}

class SALOAD : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val index = stack.popInt()
        val arrRef = stack.popRef()

        if (arrRef == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }

        val refs = arrRef.shorts
        if (index < 0 || index >= refs.size) {
            throw RuntimeException("ArrayIndexOutOfBoundsException")
        }
        stack.pushInt(refs[index].toInt())
    }

}