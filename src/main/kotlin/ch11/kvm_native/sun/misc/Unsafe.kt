package ch11.kvm_native.sun.misc

import ch11.kvm_native.KvmNative
import ch11.rtdata.KvmFrame
import ch11.rtdata.heap.KvmObject
import ch11.rtdata.heap.KvmSlots
import ch11.rtdata.heap.NODATA
import ch11.rtdata.heap.ints
import ch11.rtdata.heap.longs
import ch11.rtdata.heap.refs

private const val miscUnsafe = "sun/misc/Unsafe"

object Unsafe {
    fun init() {
        KvmNative.register(miscUnsafe, "arrayBaseOffset", "(Ljava/lang/Class;)I", ::arrayBaseOffset)
        KvmNative.register(miscUnsafe, "arrayIndexScale", "(Ljava/lang/Class;)I", ::arrayIndexScale)
        KvmNative.register(miscUnsafe, "addressSize", "()I", ::addressSize)
        KvmNative.register(miscUnsafe, "objectFieldOffset", "(Ljava/lang/reflect/Field;)J", ::objectFieldOffset)
        KvmNative.register(
            miscUnsafe,
            "compareAndSwapObject",
            "(Ljava/lang/Object;JLjava/lang/Object;Ljava/lang/Object;)Z",
            ::compareAndSwapObject
        )
        KvmNative.register(miscUnsafe, "getIntVolatile", "(Ljava/lang/Object;J)I", ::getInt)
        KvmNative.register(miscUnsafe, "compareAndSwapInt", "(Ljava/lang/Object;JII)Z", ::compareAndSwapInt)
        KvmNative.register(miscUnsafe, "getObjectVolatile", "(Ljava/lang/Object;J)Ljava/lang/Object;", ::getObject)
        KvmNative.register(miscUnsafe, "compareAndSwapLong", "(Ljava/lang/Object;JJJ)Z", ::compareAndSwapLong)
        UnsafeMem.init()
    }
}

private fun arrayBaseOffset(frame: KvmFrame) {
    frame.operandStack.pushInt(0) // TODO
}

private fun arrayIndexScale(frame: KvmFrame) {
    frame.operandStack.pushInt(1)
}

private fun addressSize(frame: KvmFrame) {
    frame.operandStack.pushInt(8)
}

private fun objectFieldOffset(frame: KvmFrame) {
    val jField = frame.localVars.getRef(1u)!!
    val offset = jField.getIntVar("slot", "I")

    frame.operandStack.pushLong(offset.toLong())
}

private fun compareAndSwapObject(frame: KvmFrame) {
    val vars = frame.localVars
    val obj = vars.getRef(1u)!!
    val fields = obj.fields
    val data = obj.data
    val offset = vars.getLong(2u)
    val expected = vars.getRef(4u)
    val newVal = vars.getRef(5u)

    // TODO
    if (data == NODATA) {
        val swapped = _casObj(fields, offset, expected, newVal)
        frame.operandStack.pushBoolean(swapped)
    } else if (data::class == Array<KvmObject?>::class) {
        val swapped = _casArr(obj.refs, offset, expected, newVal)
        frame.operandStack.pushBoolean(swapped)
    } else {
        throw RuntimeException("TODO: compareAndSwapObject!")
    }
}

private fun _casObj(fields: KvmSlots, offset: Long, expected: KvmObject?, newVal: KvmObject?): Boolean {
    val current = fields.getRef(offset.toUInt())
    if (current == expected) {
        fields.setRef(offset.toUInt(), newVal)
        return true
    } else {
        return false
    }
}

private fun _casArr(
    objs: Array<KvmObject?>,
    offset: Long,
    expected: KvmObject?,
    newVal: KvmObject?
): Boolean {
    val current = objs[offset.toInt()]
    if (current == expected) {
        objs[offset.toInt()] = newVal
        return true
    } else {
        return false
    }
}


private fun getInt(frame: KvmFrame) {
    val vars = frame.localVars
    val obj = vars.getRef(1u)!!
    val fields = obj.fields
    val data = obj.data
    val offset = vars.getLong(2u)

    val stack = frame.operandStack
    if (data == NODATA) {
        stack.pushInt(fields.getInt(offset.toUInt()))
    } else if (data::class == Array<Int>::class) {
        stack.pushInt(obj.ints[offset.toInt()])
    } else {
        throw RuntimeException("getInt!")
    }
}

private fun compareAndSwapInt(frame: KvmFrame) {
    val vars = frame.localVars
    val obj = vars.getRef(1u)!!
    val fields = obj.fields
    val data = obj.data
    val offset = vars.getLong(2u)
    val expected = vars.getInt(4u)
    val newVal = vars.getInt(5u)

    if (data == NODATA) {
        val oldVal = fields.getInt(offset.toUInt())
        if (oldVal == expected) {
            fields.setInt(offset.toUInt(), newVal)
            frame.operandStack.pushBoolean(true)
        } else {
            frame.operandStack.pushBoolean(false)
        }
    } else if (data::class == Array<Int>::class) {
        val oldVal = obj.ints[offset.toInt()]
        if (oldVal == expected) {
            obj.ints[offset.toInt()] = newVal
            frame.operandStack.pushBoolean(true)
        } else {
            frame.operandStack.pushBoolean(false)
        }
    } else {
        throw RuntimeException("TODO: compareAndSwapInt")
    }
}

private fun getObject(frame: KvmFrame) {
    val vars = frame.localVars
    val obj = vars.getRef(1u)!!
    val data = obj.data
    val offset = vars.getLong(2u)

    if (data == NODATA) {
        val fields = obj.fields
        val x = fields.getRef(offset.toUInt())
        frame.operandStack.pushRef(x)
    } else if (data::class == Array<KvmObject?>::class) {
        val x = obj.refs[offset.toInt()]
        frame.operandStack.pushRef(x)
    } else {
        throw RuntimeException("getObject!")
    }
}

private fun compareAndSwapLong(frame: KvmFrame) {
    val vars = frame.localVars
    val obj = vars.getRef(1u)!!
    val fields = obj.fields
    val data = obj.data
    val offset = vars.getLong(2u)
    val expected = vars.getLong(4u)
    val newVal = vars.getLong(6u)

    if (data == NODATA) {
        val oldVal = fields.getLong(offset.toUInt())
        if (oldVal == expected) {
            fields.setLong(offset.toUInt(), newVal)
            frame.operandStack.pushBoolean(true)
        } else {
            frame.operandStack.pushBoolean(false)
        }
    } else if (data::class == Array<Long>::class) {
        val oldVal = obj.longs[offset.toInt()]
        if (oldVal == expected) {
            obj.longs[offset.toInt()] = newVal
            frame.operandStack.pushBoolean(true)
        } else {
            frame.operandStack.pushBoolean(false)
        }
    } else {
        throw RuntimeException("TODO: compareAndSwapLong")
    }
}