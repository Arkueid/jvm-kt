package ch09.instructions.stores

import ch09.instructions.base.NoOperandsInstruction
import ch09.rtdata.KvmFrame
import ch09.rtdata.heap.bytes
import ch09.rtdata.heap.chars
import ch09.rtdata.heap.doubles
import ch09.rtdata.heap.floats
import ch09.rtdata.heap.ints
import ch09.rtdata.heap.longs
import ch09.rtdata.heap.refs
import ch09.rtdata.heap.shorts

class AASTORE : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val value = stack.popRef()
        val index = stack.popInt()
        val arrRef = stack.popRef()

        if (arrRef == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }

        val refs = arrRef.refs
        if (index < 0 || index >= refs.size) {
            throw RuntimeException("ArrayIndexOutOfBoundsException")
        }

        refs[index] = value
    }

}

class BASTORE : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val value = stack.popInt()
        val index = stack.popInt()
        val arrRef = stack.popRef()

        if (arrRef == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }

        val arr = arrRef.bytes
        if (index < 0 || index >= arr.size) {
            throw RuntimeException("ArrayIndexOutOfBoundsException")
        }

        arr[index] = value.toByte()
    }

}

class CASTORE : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val value = stack.popInt()
        val index = stack.popInt()
        val arrRef = stack.popRef()

        if (arrRef == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }

        val arr = arrRef.chars
        if (index < 0 || index >= arr.size) {
            throw RuntimeException("ArrayIndexOutOfBoundsException")
        }

        arr[index] = value.toUShort()
    }

}

class DASTORE : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val value = stack.popDouble()
        val index = stack.popInt()
        val arrRef = stack.popRef()

        if (arrRef == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }

        val arr = arrRef.doubles
        if (index < 0 || index >= arr.size) {
            throw RuntimeException("ArrayIndexOutOfBoundsException")
        }

        arr[index] = value
    }

}

class FASTORE : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val value = stack.popFloat()
        val index = stack.popInt()
        val arrRef = stack.popRef()

        if (arrRef == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }

        val arr = arrRef.floats
        if (index < 0 || index >= arr.size) {
            throw RuntimeException("ArrayIndexOutOfBoundsException")
        }

        arr[index] = value
    }

}

class IASTORE : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val value = stack.popInt()
        val index = stack.popInt()
        val ref = stack.popRef()

        if (ref == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }

        val ints = ref.ints
        if (index < 0 || index >= ints.size) {
            throw RuntimeException("ArrayIndexOutOfBoundsException")
        }

        ints[index] = value
    }

}

class LASTORE : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val value = stack.popLong()
        val index = stack.popInt()
        val arrRef = stack.popRef()

        if (arrRef == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }

        val arr = arrRef.longs
        if (index < 0 || index >= arr.size) {
            throw RuntimeException("ArrayIndexOutOfBoundsException")
        }

        arr[index] = value
    }

}

class SASTORE : NoOperandsInstruction() {
    override fun execute(frame: KvmFrame) {
        val stack = frame.operandStack
        val value = stack.popInt()
        val index = stack.popInt()
        val arrRef = stack.popRef()

        if (arrRef == null) {
            throw RuntimeException("java.lang.NullPointerException")
        }

        val arr = arrRef.shorts
        if (index < 0 || index >= arr.size) {
            throw RuntimeException("ArrayIndexOutOfBoundsException")
        }

        arr[index] = value.toShort()
    }

}