package week5.lecture12;

public class ArgsSum {
    /**
     * Prints out the sum of arguments, assuming they are integers.
     * 測試
     */
    public static void main(String[] args) {
        int index = 0;
        int sum = 0;
        while (index < args.length) {
            sum = sum + Integer.parseInt(args[index]);
            index += 1;
        }
        System.out.println(sum);
    }
}
