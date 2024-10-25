package ch07.rtdata.heap

class KvmMethodDescriptor {
    private var size = 0

    fun addParameterType(t: String) {
        parameterTypes[size] = t
        size++
    }

    lateinit var parameterTypes: Array<String>
    lateinit var returnType: String

}