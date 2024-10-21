package ch06.rtdata.heap

import ch03.classfile.ConstantMemberRefInfo

class KvmMemberRef : KvmSymRef() {
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