package week7.discussion;

import java.util.TreeSet;

/**
 * Given an int x and a sorted array A of N distinct integers, design an algorithm to find if there exists
 * indices i and j such that A[i] + A[j] == x.
 */
public class FindSum {

    /**
     * O(N^2)
     */
    public static boolean findSumNaive(int[] A, int x) {
        for (int i = 0; i < A.length; i++){
            for (int j = 0; j < A.length; j++) {
                if (A[i] + A[j] == x) return true;
            }
        }
        return false;
    }

    /**
     * O(N*log(N))
     */
    public static boolean findSumImproved(int[] A, int x) {
        TreeSet<Integer> B = new TreeSet<>();
        for (int i = 0; i < A.length; i++) {
            B.add(x - A[i]);
            if (B.contains(A[i])) return true;
        }
        return false;
    }

    /**
     * O(N)
     */
    public static boolean findSumFaster(int[] A, int x) {
        int left = 0;
        int right = A.length - 1;
        while (left != right) {
            if (A[left] + A[right] == x) {
                return true;
            } else if (A[left] + A[right] > x) {
                right--;
            } else {
                left++;
            }
        }
        return false;
    }
}
