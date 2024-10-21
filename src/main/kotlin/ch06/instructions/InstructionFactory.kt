package ch06.instructions

import ch06.instructions.base.Instruction
import ch06.instructions.constants.*
import ch06.instructions.loads.*
import ch06.instructions.stack.*
import ch06.instructions.stores.*
import ch06.instructions.comparisons.*
import ch06.instructions.control.*
import ch06.instructions.conversions.*
import ch06.instructions.math.*
import ch06.instructions.extended.*


// 单例
val nop = NOP()
val aconst_null = ACONST_NULL()
val iconst_m1 = ICONST_M1()
val iconst_0 = ICONST_0()
val iconst_1 = ICONST_1()
val iconst_2 = ICONST_2()
val iconst_3 = ICONST_3()
val iconst_4 = ICONST_4()
val iconst_5 = ICONST_5()
val lconst_0 = LCONST_0()
val lconst_1 = LCONST_1()
val fconst_0 = FCONST_0()
val fconst_1 = FCONST_1()
val fconst_2 = FCONST_2()
val dconst_0 = DCONST_0()
val dconst_1 = DCONST_1()
val iload_0 = ILOAD_0()
val iload_1 = ILOAD_1()
val iload_2 = ILOAD_2()
val iload_3 = ILOAD_3()
val lload_0 = LLOAD_0()
val lload_1 = LLOAD_1()
val lload_2 = LLOAD_2()
val lload_3 = LLOAD_3()
val fload_0 = FLOAD_0()
val fload_1 = FLOAD_1()
val fload_2 = FLOAD_2()
val fload_3 = FLOAD_3()
val dload_0 = DLOAD_0()
val dload_1 = DLOAD_1()
val dload_2 = DLOAD_2()
val dload_3 = DLOAD_3()
val aload_0 = ALOAD_0()
val aload_1 = ALOAD_1()
val aload_2 = ALOAD_2()
val aload_3 = ALOAD_3()

// iaload = IALOAD()
// laload = LALOAD()
// faload = FALOAD()
// daload = DALOAD()
// aaload = AALOAD()
// baload = BALOAD()
// caload = CALOAD()
// saload = SALOAD()
val istore_0 = ISTORE_0()
val istore_1 = ISTORE_1()
val istore_2 = ISTORE_2()
val istore_3 = ISTORE_3()
val lstore_0 = LSTORE_0()
val lstore_1 = LSTORE_1()
val lstore_2 = LSTORE_2()
val lstore_3 = LSTORE_3()
val fstore_0 = FSTORE_0()
val fstore_1 = FSTORE_1()
val fstore_2 = FSTORE_2()
val fstore_3 = FSTORE_3()
val dstore_0 = DSTORE_0()
val dstore_1 = DSTORE_1()
val dstore_2 = DSTORE_2()
val dstore_3 = DSTORE_3()
val astore_0 = ASTORE_0()
val astore_1 = ASTORE_1()
val astore_2 = ASTORE_2()
val astore_3 = ASTORE_3()

// iastore  = IASTORE()
// lastore  = LASTORE()
// fastore  = FASTORE()
// dastore  = DASTORE()
// aastore  = AASTORE()
// bastore  = BASTORE()
// castore  = CASTORE()
// sastore  = SASTORE()
val pop = POP()
val pop2 = POP2()
val dup = DUP()
val dup_x1 = DUP_X1()
val dup_x2 = DUP_X2()
val dup2 = DUP2()
val dup2_x1 = DUP2_X1()
val dup2_x2 = DUP2_X2()
val swap = SWAP()
val iadd = IADD()
val ladd = LADD()
val fadd = FADD()
val dadd = DADD()
val isub = ISUB()
val lsub = LSUB()
val fsub = FSUB()
val dsub = DSUB()
val imul = IMUL()
val lmul = LMUL()
val fmul = FMUL()
val dmul = DMUL()
val idiv = IDIV()
val ldiv = LDIV()
val fdiv = FDIV()
val ddiv = DDIV()
val irem = IREM()
val lrem = LREM()
val frem = FREM()
val drem = DREM()
val ineg = INEG()
val lneg = LNEG()
val fneg = FNEG()
val dneg = DNEG()
val ishl = ISHL()
val lshl = LSHL()
val ishr = ISHR()
val lshr = LSHR()
val iushr = IUSHR()
val lushr = LUSHR()
val iand = IAND()
val land = LAND()
val ior = IOR()
val lor = LOR()
val ixor = IXOR()
val lxor = LXOR()
val i2l = I2L()
val i2f = I2F()
val i2d = I2D()
val l2i = L2I()
val l2f = L2F()
val l2d = L2D()
val f2i = F2I()
val f2l = F2L()
val f2d = F2D()
val d2i = D2I()
val d2l = D2L()
val d2f = D2F()
val i2b = I2B()
val i2c = I2C()
val i2s = I2S()
val lcmp = LCMP()
val fcmpl = FCMPL()
val fcmpg = FCMPG()
val dcmpl = DCMPL()
val dcmpg = DCMPG()
// i = IRETURN()
// l = LRETURN()
// f = FRETURN()
// d = DRETURN()
// a = ARETURN()
// _ = RETURN()
// arraylength   = ARRAY_LENGTH()
// athrow   = ATHROW()
// monitorenter  = MONITOR_ENTER()
// monitorexit   = MONITOR_EXIT()
// invoke_native = INVOKE_NATIVE()

object InstructionFactory {
    @OptIn(ExperimentalStdlibApi::class)
    fun create(opcode: UByte): Instruction {
        return when (opcode.toInt() and 0xFF) {
            0x00 -> nop
            0x01 -> aconst_null
            0x02 -> iconst_m1
            0x03 -> iconst_0
            0x04 -> iconst_1
            0x05 -> iconst_2
            0x06 -> iconst_3
            0x07 -> iconst_4
            0x08 -> iconst_5
            0x09 -> lconst_0
            0x0a -> lconst_1
            0x0b -> fconst_0
            0x0c -> fconst_1
            0x0d -> fconst_2
            0x0e -> dconst_0
            0x0f -> dconst_1
            0x10 -> BIPUSH()
            0x11 -> SIPUSH()
            // 0x12 ->     // 	 LDC()
            // 0x13 ->     // 	 LDC_W()
            // 0x14 ->     // 	 LDC2_W()
            0x15 -> ILOAD()
            0x16 -> LLOAD()
            0x17 -> FLOAD()
            0x18 -> DLOAD()
            0x19 -> ALOAD()
            0x1a -> iload_0
            0x1b -> iload_1
            0x1c -> iload_2
            0x1d -> iload_3
            0x1e -> lload_0
            0x1f -> lload_1
            0x20 -> lload_2
            0x21 -> lload_3
            0x22 -> fload_0
            0x23 -> fload_1
            0x24 -> fload_2
            0x25 -> fload_3
            0x26 -> dload_0
            0x27 -> dload_1
            0x28 -> dload_2
            0x29 -> dload_3
            0x2a -> aload_0
            0x2b -> aload_1
            0x2c -> aload_2
            0x2d -> aload_3
            // 0x2e ->     // 	 iaload
            // 0x2f ->     // 	 laload
            // 0x30 ->     // 	 faload
            // 0x31 ->     // 	 daload
            // 0x32 ->     // 	 aaload
            // 0x33 ->     // 	 baload
            // 0x34 ->     // 	 caload
            // 0x35 ->     // 	 saload
            0x36 -> ISTORE()
            0x37 -> LSTORE()
            0x38 -> FSTORE()
            0x39 -> DSTORE()
            0x3a -> ASTORE()
            0x3b -> istore_0
            0x3c -> istore_1
            0x3d -> istore_2
            0x3e -> istore_3
            0x3f -> lstore_0
            0x40 -> lstore_1
            0x41 -> lstore_2
            0x42 -> lstore_3
            0x43 -> fstore_0
            0x44 -> fstore_1
            0x45 -> fstore_2
            0x46 -> fstore_3
            0x47 -> dstore_0
            0x48 -> dstore_1
            0x49 -> dstore_2
            0x4a -> dstore_3
            0x4b -> astore_0
            0x4c -> astore_1
            0x4d -> astore_2
            0x4e -> astore_3
            // 0x4f ->     // 	 iastore
            // 0x50 ->     // 	 lastore
            // 0x51 ->     // 	 fastore
            // 0x52 ->     // 	 dastore
            // 0x53 ->     // 	 aastore
            // 0x54 ->     // 	 bastore
            // 0x55 ->     // 	 castore
            // 0x56 ->     // 	 sastore
            0x57 -> pop
            0x58 -> pop2
            0x59 -> dup
            0x5a -> dup_x1
            0x5b -> dup_x2
            0x5c -> dup2
            0x5d -> dup2_x1
            0x5e -> dup2_x2
            0x5f -> swap
            0x60 -> iadd
            0x61 -> ladd
            0x62 -> fadd
            0x63 -> dadd
            0x64 -> isub
            0x65 -> lsub
            0x66 -> fsub
            0x67 -> dsub
            0x68 -> imul
            0x69 -> lmul
            0x6a -> fmul
            0x6b -> dmul
            0x6c -> idiv
            0x6d -> ldiv
            0x6e -> fdiv
            0x6f -> ddiv
            0x70 -> irem
            0x71 -> lrem
            0x72 -> frem
            0x73 -> drem
            0x74 -> ineg
            0x75 -> lneg
            0x76 -> fneg
            0x77 -> dneg
            0x78 -> ishl
            0x79 -> lshl
            0x7a -> ishr
            0x7b -> lshr
            0x7c -> iushr
            0x7d -> lushr
            0x7e -> iand
            0x7f -> land
            0x80 -> ior
            0x81 -> lor
            0x82 -> ixor
            0x83 -> lxor
            0x84 -> IINC()
            0x85 -> i2l
            0x86 -> i2f
            0x87 -> i2d
            0x88 -> l2i
            0x89 -> l2f
            0x8a -> l2d
            0x8b -> f2i
            0x8c -> f2l
            0x8d -> f2d
            0x8e -> d2i
            0x8f -> d2l
            0x90 -> d2f
            0x91 -> i2b
            0x92 -> i2c
            0x93 -> i2s
            0x94 -> lcmp
            0x95 -> fcmpl
            0x96 -> fcmpg
            0x97 -> dcmpl
            0x98 -> dcmpg
            0x99 -> IFEQ()
            0x9a -> IFNE()
            0x9b -> IFLT()
            0x9c -> IFGE()
            0x9d -> IFGT()
            0x9e -> IFLE()
            0x9f -> IF_ICMPEQ()
            0xa0 -> IF_ICMPNE()
            0xa1 -> IF_ICMPLT()
            0xa2 -> IF_ICMPGE()
            0xa3 -> IF_ICMPGT()
            0xa4 -> IF_ICMPLE()
            0xa5 -> IF_ACMPEQ()
            0xa6 -> IF_ACMPNE()
            0xa7 -> GOTO()
            // 0xa8 ->     // 	 JSR()
            // 0xa9 ->     // 	 RET()
            0xaa -> TABLE_SWITCH()
            0xab -> LOOKUP_SWITCH()
            // 0xac ->     // 	 i
            // 0xad ->     // 	 l
            // 0xae ->     // 	 f
            // 0xaf ->     // 	 d
            // 0xb0 ->     // 	 a
            // 0xb1 ->     // 	 _
            //	0xb2 ->     //		 GET_STATIC()
            // 0xb3 ->     // 	 PUT_STATIC()
            // 0xb4 ->     // 	 GET_FIELD()
            // 0xb5 ->     // 	 PUT_FIELD()
            //	0xb6 ->     //		 INVOKE_VIRTUAL()
            // 0xb7 ->     // 	 INVOKE_SPECIAL()
            // 0xb8 ->     // 	 INVOKE_STATIC()
            // 0xb9 ->     // 	 INVOKE_INTERFACE()
            // 0xba ->     // 	 INVOKE_DYNAMIC()
            // 0xbb ->     // 	 NEW()
            // 0xbc ->     // 	 NEW_ARRAY()
            // 0xbd ->     // 	 ANEW_ARRAY()
            // 0xbe ->     // 	 arraylength
            // 0xbf ->     // 	 athrow
            // 0xc0 ->     // 	 CHECK_CAST()
            // 0xc1 ->     // 	 INSTANCE_OF()
            // 0xc2 ->     // 	 monitorenter
            // 0xc3 ->     // 	 monitorexit
            0xc4 -> WIDE()
            // 0xc5 ->     // 	 MULTI_ANEW_ARRAY()
            0xc6 -> IFNULL()
            0xc7 -> IFNONNULL()
            0xc8 -> GOTO_W()
            // 0xc9 ->     // 	 JSR_W()
            // case 0xca: breakpoint
            // case 0xfe: impdep1
            // case 0xff: impdep2
            else -> throw RuntimeException("Unsupported opcode: 0x${opcode.toHexString()}")
        }
    }

}