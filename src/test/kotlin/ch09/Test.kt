package ch09

import ch09.rtdata.heap.KvmObject
import ch09.rtdata.heap.kvmArrayCopy

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        val obj1 = KvmObject(data = arrayOf(0, 0, 0))
        val obj2 = KvmObject(data = Array<Any?>(3) { null })
        kvmArrayCopy(obj1, 0, obj2, 0, 3)

        println(obj2.data.joinToString())
    }
}