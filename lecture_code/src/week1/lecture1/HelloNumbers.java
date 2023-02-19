package week1.lecture1;

public class HelloNumbers {
    public static void main(String[] args) {
        int x; // Declare x exists and is an integer
        x = 0;
        while(x < 10) {
            System.out.println(x);
            x = x + 1;
        }
    }
}

/**
 * 1. Before Java variable can be used, they must be declared.
 * 2. Java variable must have a specific type.
 * 3. Java variable types can never change.
 * 4. In Java, types are verified before the code runs!
 *    If there are types issues, the code WILL NOT COMPILE!
 * */

/**
 * comparison to Python
 * Python code can crash due to type errors when it's running!
 * so e.g. if someone is running a version of tiktok on their phone written in Python(which is not possible),
 * then it could crash in the middle of making a sick dirt bike video due to type errors.
 * */