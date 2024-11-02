package ch11.kvm_native.sun.misc

import ch11.kvm_native.KvmNative
import ch11.kvm_native.NativeMethod
import ch11.rtdata.KvmFrame
import java.nio.ByteBuffer

object UnsafeMem {
    fun init() {
        _unsafe(::allocateMemory, "allocateMemory", "(J)J")
        _unsafe(::reallocateMemory, "reallocateMemory", "(JJ)J")
        _unsafe(::freeMemory, "freeMemory", "(J)V")
        //	_unsafe(putAddress, "putAddress", "(JJ)V")
        //	_unsafe(getAddress, "getAddress", "(J)J")
        //	_unsafe(mem_putByte, "putByte", "(JB)V")
        _unsafe(::mem_getByte, "getByte", "(J)B")
        //	_unsafe(mem_putShort, "putShort", "(JS)V")
        //	_unsafe(mem_getShort, "getShort", "(J)S")
        //	_unsafe(mem_putChar, "putChar", "(JC)V")
        //	_unsafe(mem_getChar, "getChar", "(J)C")
        //	_unsafe(mem_putInt, "putInt", "(JI)V")
        //	_unsafe(mem_getInt, "getInt", "(J)I")
        _unsafe(::mem_putLong, "putLong", "(JJ)V")
        //	_unsafe(mem_getLong, "getLong", "(J)J")
        //	_unsafe(mem_putFloat, "putFloat", "(JF)V")
        //	_unsafe(mem_getFloat, "getFloat", "(J)F")
        //	_unsafe(mem_putDouble, "putDouble", "(JD)V")
        //	_unsafe(mem_getDouble, "getDouble", "(J)D")
    }
}

private fun _unsafe(method: NativeMethod, name: String, desc: String) {
    KvmNative.register("sun/misc/Unsafe", name, desc, method)
}

private val vMem = mutableMapOf<Long, ByteBuffer>()

private fun allocate(size: Long): Long {
    // TODO
    val address = System.currentTimeMillis()
    vMem[address] = ByteBuffer.allocate(size.toInt())
    return address
}

private fun allocateMemory(frame: KvmFrame) {
    val vars = frame.localVars
    val bytes = vars.getLong(1u)

    val address = allocate(bytes)
    frame.operandStack.pushLong(address)
}

private fun reallocateMemory(frame: KvmFrame) {
    val vars = frame.localVars
    val address = vars.getLong(1u)
    val bytes = vars.getLong(3u)

    val newAddress = reallocate(address, bytes)
    frame.operandStack.pushLong(newAddress)
}

fun reallocate(address: Long, size: Long): Long {
    vMem.remove(address)
    val newAddr = System.currentTimeMillis()
    vMem[newAddr] = ByteBuffer.allocate(size.toInt())
    return newAddr
}

private fun freeMemory(frame: KvmFrame) {
    val addr = frame.localVars.getLong(1u)
    free(addr)
}

private fun free(address: Long) {
    vMem.remove(address)
}

private fun mem_getByte(frame: KvmFrame) {
    val addr = frame.localVars.getLong(1u)
    val mem = memoryAt(addr)
    mem.rewind()
    frame.operandStack.pushInt(mem.get().toInt())
}

private fun memoryAt(address: Long): ByteBuffer {
    return vMem[address]!!
}

private fun mem_putLong(frame: KvmFrame) {
    val addr = frame.localVars.getLong(1u)
    val value = frame.localVars.getLong(3u)
    val mem = memoryAt(addr)
    mem.rewind()
    mem.putLong(value)
}