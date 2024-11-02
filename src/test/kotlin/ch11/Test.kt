package ch11

import ch11.rtdata.heap.KvmObject
import java.nio.ByteBuffer

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        var buf = ByteBuffer.allocate(8)
        buf.flip()
        println(buf.putLong(100))
    }

    fun test(arr: Array<out Any?>) {
        println(arr::class == Array<KvmObject?>::class)
    }
}
