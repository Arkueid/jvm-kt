package ch11.rtdata.heap

class KvmMethodDescriptor {
    fun addParameterType(t: String) {
        parameterTypes.add(t)
    }

    var parameterTypes: MutableList<String> = mutableListOf()
    lateinit var returnType: String

}