package ch07

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        val a : Byte = -1
        println(a.toUInt() and 0xffu)
    }
}