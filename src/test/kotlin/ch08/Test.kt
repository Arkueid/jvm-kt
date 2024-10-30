package ch08

import ch08.rtdata.KvmOperandStack

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        val chars = Array(10) { 'A'.toChar() }
        println(chars.toString())
        println(chars.joinToString(separator = ""))
    }
}