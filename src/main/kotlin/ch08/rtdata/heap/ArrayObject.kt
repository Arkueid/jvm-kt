package ch08.rtdata.heap

// array object
val KvmObject.bytes: Array<Byte> get() = data as Array<Byte>
val KvmObject.shorts: Array<Short> get() = data as Array<Short>
val KvmObject.ints: Array<Int> get() = data as Array<Int>
val KvmObject.longs: Array<Long> get() = data as Array<Long>
val KvmObject.chars: Array<Char> get() = data as Array<Char>
val KvmObject.floats: Array<Float> get() = data as Array<Float>
val KvmObject.doubles: Array<Double> get() = data as Array<Double>
val KvmObject.refs: Array<KvmObject> get() = data as Array<KvmObject>
val KvmObject.arrayLength get() = data.size
