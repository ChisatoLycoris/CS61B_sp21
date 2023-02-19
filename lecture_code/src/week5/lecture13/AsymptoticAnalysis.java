package week5.lecture13;

public class AsymptoticAnalysis {

    /**
     * operation       symbolic count
     * i = 0           1
     * j = i + 1       1 to N
     * less than(<)    2 to (N^2+3N+2)/2
     * increment(++)   0 to (N^2+N)/2
     * equals(==)      1 to (N^2-N)/2
     * array access    2 to N^2-N
     *
     * dup1: parabolic a.k.a. quadratic
     * O(N^2)
     */
    public static boolean dup1(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] == a[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * operation       symbolic count
     * i = 0           1
     * less than(<)    0 to N
     * increment(++)   0 to N-1
     * equals(==)      1 to N-1
     * array access    2 to 2N-2
     *
     * dup2: linear
     * O(N)
     */
    public static boolean dup2(int[] a) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == a[i + 1]) {
                return true;
            }
        }
        return false;
    }

    public static int[] makeArray(int n) {
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = i;
        }
        return result;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int[] A = makeArray(N);
        dup1(A);
    }
}
