package lecture1;

public class LargerDemo {
    public static int larger(int x, int y) {
        if (x > y) {
            return x;
        }
        return y;
    }
    public static double larger(double x, double y) {
        if (x > y) {
            return x;
        }
        return y;
    }
    public static void main(String[] args) {
        System.out.print(larger(5, -10));
    }
}

/**
 * 1. To declare a function in Java, use "public static " (for today).
 * 2. All parameter of a function must have a type, and the function itself must have a return type.
 * 3. All functions must be part of a class.
 *    In programming language terminology, a function that is a part of a class is called a "method",
 *    so all functions in Java are methods.
 * */