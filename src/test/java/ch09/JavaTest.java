package ch09;

public class JavaTest {
    public static void main(String[] args) {
        JavaTest t1 = new JavaTest();
        JavaTest t2 = new JavaTest();
        System.out.println(int.class);
        System.out.println(t1.getClass().hashCode());
        System.out.println(t2.getClass().hashCode());
    }
}
