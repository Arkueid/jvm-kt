package ch04

import ch05.rtdata.KvmFrame
import ch05.rtdata.KvmLocalVars
import ch05.rtdata.KvmOperandStack
import ch05.rtdata.KvmThread

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        val frame = KvmFrame(KvmThread(), 100u, 100u)
        testLocalVars(frame.localVars)
        testOperandStack(frame.operandStack)
    }

    private fun testLocalVars(vars: KvmLocalVars) {
        vars.setInt(0u, 100)
        vars.setInt(1u, -100)
        vars.setLong(2u, 2997924580)
        vars.setLong(4u, -2997924580)
        vars.setFloat(6u, 3.1415926f) // 精度不够
        vars.setDouble(7u, 2.71828182845)
        vars.setRef(9u, null)

        println(vars.getInt(0u))
        println(vars.getInt(1u))
        println(vars.getLong(2u))
        println(vars.getLong(4u))
        println(vars.getFloat(6u))
        println(vars.getDouble(7u))
        println(vars.getRef(9u))
    }

    private fun testOperandStack(ops: KvmOperandStack) {
        ops.pushInt(100)
        ops.pushInt(-100)
        ops.pushLong(2997924580)
        ops.pushLong(-2997924580)
        ops.pushFloat(3.1415926f)
        ops.pushDouble(2.71828182845)
        ops.pushRef(null)

        println(ops.popRef())
        println(ops.popDouble())
        println(ops.popFloat())
        println(ops.popLong())
        println(ops.popLong())
        println(ops.popInt())
        println(ops.popInt())
    }
}