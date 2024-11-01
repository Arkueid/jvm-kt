package ch10.kvm_native.java.lang

import ch10.kvm_native.KvmNative
import ch10.rtdata.KvmFrame
import ch10.rtdata.heap.KvmObject
import ch10.rtdata.heap.isImplements

object Object {
    @JvmStatic
    fun init() {
        KvmNative.register("java/lang/Object", "getClass", "()Ljava/lang/Class;", ::getClass)
        KvmNative.register("java/lang/Object", "hashCode", "()I", ::hashCode)
        KvmNative.register("java/lang/Object", "clone", "()Ljava/lang/Object;", ::clone)
    }
}

// public final native Class<?> getClass();
private fun getClass(frame: KvmFrame) {
    val _this = frame.localVars.getRef(0u)
    val jCass = _this!!.klass.jClass
    frame.operandStack.pushRef(jCass)
}


// public native int hashCode();
private fun hashCode(frame: KvmFrame) {
    val _this = frame.localVars.getRef(0u)!!
    val hash = myHashCode(_this)
    frame.operandStack.pushInt(hash)
}

private fun clone(frame: KvmFrame) {
    val _this = frame.localVars.getRef(0u)!!
    val cloneable = _this.klass.loader.loadClass("java/lang/Cloneable")
    if (!_this.klass.isImplements(cloneable)) {
        throw RuntimeException("java.lang.CloneNotSupportedException")
    }

    frame.operandStack.pushRef(_this.clone())
}


private val hashCodeMap = mutableMapOf<Int, Int>()

// enable us to predict the result, though smelly
var startCode = 114514
private fun myHashCode(obj: KvmObject): Int {
    return hashCodeMap[obj.hashCode()] ?: run {
        hashCodeMap[obj.hashCode()] = startCode
        startCode++
    }
}