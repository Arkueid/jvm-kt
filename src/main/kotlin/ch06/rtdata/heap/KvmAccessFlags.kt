package ch06.rtdata.heap

object KvmAccessFlags {
    const val ACC_PUBLIC: UShort = 0x0001u //    class   field   method
    const val ACC_PRIVATE: UShort = 0x002u //            field   method
    const val ACC_PROTECTED: UShort = 0x004u //          field   method
    const val ACC_STATIC: UShort = 0x0008u //            field   method
    const val ACC_FINAL: UShort = 0x00010u //    class   field   method
    const val ACC_SUPER: UShort = 0x0020u //     class 
    const val ACC_SYNCHRONIZED: UShort = 0x0020u // method
    const val ACC_VOLATILE: UShort = 0x0040u // field
    const val ACC_BRIDGE: UShort = 0x0040u // method
    const val ACC_TRANSIENT: UShort = 0x0080u // field
    const val ACC_VARARGS: UShort = 0x0080u // method
    const val ACC_NATIVE: UShort = 0x0100u // method
    const val ACC_INTERFACE: UShort = 0x0200u // class
    const val ACC_ABSTRACT: UShort = 0x0400u // class method
    const val ACC_STRICT: UShort = 0x0800u // method
    const val ACC_SYNTHETIC: UShort = 0x1000u // class field method
    const val ACC_ANNOTATION: UShort = 0x2000u // class
    const val ACC_ENUM: UShort = 0x4000u // class field
}
