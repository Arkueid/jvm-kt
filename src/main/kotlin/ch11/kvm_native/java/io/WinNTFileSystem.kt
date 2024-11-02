package ch11.kvm_native.java.io

import ch11.kvm_native.KvmNative
import ch11.rtdata.KvmFrame
import ch11.rtdata.heap.KvmObject
import ch11.rtdata.heap.kvmStrFromJStr
import java.io.File
import java.nio.file.Files

object WinNTFileSystem {
    fun init() = KvmNative.register(
        "java/io/WinNTFileSystem", "getBooleanAttributes",
        "(Ljava/io/File;)I", ::getBooleanAttributes
    )
}

private fun getBooleanAttributes(frame: KvmFrame) {
    val vars = frame.localVars
    val f = vars.getRef(1u)!!
    val path = _getPath(f)

    var attributes = 0
    if (_exists(path)) {
        attributes = attributes or 0x01
    }
    if (_isDir(path)) {
        attributes = attributes or 0x04
    }

    frame.operandStack.pushInt(attributes)
}

private fun _isDir(path: String): Boolean {
    return File(path).isDirectory
}

private fun _exists(path: String): Boolean {
    return File(path).exists()
}

private fun _getPath(fileObj: KvmObject): String {
    val pathStr = fileObj.getRefVar("path", "Ljava/lang/String;")
    return kvmStrFromJStr(pathStr)!!
}
