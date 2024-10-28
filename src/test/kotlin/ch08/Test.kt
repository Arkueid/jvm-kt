package ch08

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        val a: Array<out Any?> = arrayOf(1, 2, 3, 4)
        val ints = a as Array<Int>
        println(ints.get(3))
    }
}