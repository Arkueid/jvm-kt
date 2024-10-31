package ch09

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        println(::main::class)
    }
}