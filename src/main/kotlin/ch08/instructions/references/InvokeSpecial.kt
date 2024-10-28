package ch08.instructions.references

import ch08.instructions.base.Index16Instruction
import ch08.instructions.base.invokeMethod
import ch08.rtdata.KvmFrame
import ch08.rtdata.heap.KvmMethod
import ch08.rtdata.heap.KvmMethodRef
import ch08.rtdata.heap.lookupMethodInClass

// invoke:
// special handling for super class
// private and instance initialization method
class INVOKE_SPECIAL : Index16Instruction() {
    override fun execute(frame: KvmFrame) {
        val currentClass = frame.method.klass
        val cp = currentClass.constantPool
        val methodRef = cp.getConstant(index) as KvmMethodRef
        val resolvedClass = methodRef.resolvedClass
        val resolvedMethod = methodRef.resolvedMethod

        // 正常情况下，不会触发这种错误？编译器会保证安全？
        if (resolvedMethod.name == "<init>" && resolvedMethod.klass != methodRef.klass) {
            throw RuntimeException("java.lang.NoSuchMethodError")
        }

        if (resolvedMethod.isStatic) {
            throw RuntimeException("java.lang.IncompatibleClassChangeError")
        }

        val ref = frame.operandStack.getRefFromTop(resolvedMethod.argSlotCount - 1)
        if (ref == null) {
            // obj.method(), but obj is null
            throw RuntimeException("java.lang.NullPointerException")
        }

        // TODO: ?
        //  调用父类方法
        //  protected -> 要么同类，要么被调用的方法所属类为当前执行类的父类 互斥
        //  同一个包，不同包则只能是 存在继承关系 互斥
        if (resolvedMethod.isProtected
            && resolvedMethod.klass.isSuperClassOf(currentClass)
            && resolvedMethod.klass.packageName != currentClass.packageName
            && ref.klass != currentClass
            && !ref.klass.isSubClassOf(currentClass)
        ) {
            throw RuntimeException("java.lang.IllegalAccessError")
        }

        var methodToBeInvoked: KvmMethod? = resolvedMethod

        // 正在调用超类的非初始化方法
        if (currentClass.isSuper && // 存在父类
            resolvedClass.isSuperClassOf(currentClass) && // 被调用的方法是父类的方法
            resolvedMethod.name != "<init>"
        ) {
            methodToBeInvoked = lookupMethodInClass(currentClass.superClass!!, methodRef.name, methodRef.descriptor)
        }

        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract) {
            throw RuntimeException("java.lang.AbstractMethodError")
        }

        invokeMethod(frame, methodToBeInvoked)
    }
}