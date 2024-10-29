package ch08.instructions.loads

import ch08.instructions.base.NoOperandsInstruction
import ch08.rtdata.KvmFrame
import ch08.rtdata.heap.bytes
import ch08.rtdata.heap.chars
import ch08.rtdata.heap.doubles
import ch08.rtdata.heap.floats
import ch08.rtdata.heap.ints
import ch08.rtdata.heap.longs
import ch08.rtdata.heap.refs
import ch08.rtdata.heap.shorts

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
        stack.pushInt(refs[index].code)
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