package ch09.rtdata.heap

import ch09.classfile.ConstantMemberRefInfo

open class KvmMemberRef : KvmSymRef() {
    lateinit var name: String
    lateinit var descriptor: String

    fun copyMemberRefInfo(info: ConstantMemberRefInfo) {
        className = info.className
        info.nameAndDescriptor.let {
            name = it[0]
            descriptor = it[1]
        }
    }
}