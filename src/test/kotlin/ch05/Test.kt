package ch05

object Test {

    @JvmStatic
    fun main(args: Array<String>) {
        println((999999999).toShort().toInt())
        println((999999999).toShort())
        println(((999999999) and 0x8000FFFF.toInt()).toInt())
    }
}