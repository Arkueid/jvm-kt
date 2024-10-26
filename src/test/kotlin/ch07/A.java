package ch07;

interface I {
    void interfaceMethod();
}

class P {
    public void parentMethod() {
    }
}

public class A extends P implements I {
    public static void publicStaticMethod() {
    }

    public void publicInstanceMethod() {
    }

    private void privateInstanceMethod() {
    }

    private static void privateStaticMethod() {
    }

    @Override
    public void interfaceMethod() {
    }
}
