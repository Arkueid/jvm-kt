package ch09;


class A {
}

class B {
}

public class JavaTest {
    public static void main(String[] args) {
//        JavaTest t1 = new JavaTest();
//        JavaTest t2 = new JavaTest();
//        System.out.println(int.class);
//        System.out.println(t1.getClass().hashCode());
//        System.out.println(t2.getClass().hashCode());

        A[] aArr = new A[19];
//        aArr[10] = (A) (new B());
        A[] bArr = new A[19];

        System.arraycopy(aArr, 0, bArr, 0, 19);

        System.out.println(bArr[10].getClass());
    }
}
