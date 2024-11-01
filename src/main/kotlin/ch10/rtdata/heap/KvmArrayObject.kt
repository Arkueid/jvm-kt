package ch10.rtdata.heap

// array object
val KvmObject.bytes: Array<Byte> get() = data as Array<Byte>
val KvmObject.shorts: Array<Short> get() = data as Array<Short>
val KvmObject.ints: Array<Int> get() = data as Array<Int>
val KvmObject.longs: Array<Long> get() = data as Array<Long>
val KvmObject.chars: Array<UShort> get() = data as Array<UShort>
val KvmObject.floats: Array<Float> get() = data as Array<Float>
val KvmObject.doubles: Array<Double> get() = data as Array<Double>
val KvmObject.refs: Array<KvmObject?> get() = data as Array<KvmObject?>
val KvmObject.arrayLength get() = data.size

fun kvmArrayCopy(
    src: KvmObject,
    srcPos: Int,
    dst: KvmObject,
    dstPos: Int,
    length: Int
) {
    // >V<
    System.arraycopy(src.data, srcPos, dst.data, dstPos, length)
}
