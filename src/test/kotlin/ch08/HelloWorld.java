package ch08;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        for (String s : args) {
            System.out.println(s);
        }
    }
}
