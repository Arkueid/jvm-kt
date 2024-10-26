package ch07;

class FibonacciTest {
    public static void main(String[] args) {
        long x = fibonacci(30);
        System.out.println(x);
    }

    private static long fibonacci(int i) {
        if (i <= 1) {
            return i;
        }
        return fibonacci(i - 1) + fibonacci(i - 2);
    }
}