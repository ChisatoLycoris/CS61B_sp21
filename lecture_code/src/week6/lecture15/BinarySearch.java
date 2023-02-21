package week6.lecture15;

public class BinarySearch {

    /**
     * Bug in the binarySearch() : int mid = (low + high) / 2;
     * It fails if the sum of low and high is greater than the maximum positive int value (2^31 - 1)
     * To fix this bug:
     * int mid = low + ((high - low) / 2);
     * or probably faster:
     * int mid = (low + high) >>> 1
     */
    public static int binarySearch(int[] a, int key) {
        int low = 0;
        int high = a.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int midVal = a[mid];

            if (midVal < key) {
                low = mid + 1;
            } else if (midVal > key) {
                high = mid - 1;
            } else {
                // key found
                return mid;
            }
        }
        // key not found
        return - (low + 1);
    }

    public  static int binarySearchRecursive(String[] sorts, String x) {
        return binarySearchRecursive(sorts, x, 0, sorts.length - 1);
    }

    private static int binarySearchRecursive(String[] sorts, String x, int low, int high) {
        if (low > high) {
            // key not found
            return -1;
        }
        int mid = (low + high) / 2;
        int compare = x.compareTo(sorts[mid]);
        if (compare < 0) {
            return binarySearchRecursive(sorts, x, low, mid - 1);
        } else if (compare > 0) {
            return binarySearchRecursive(sorts, x, mid + 1, high);
        } else {
            // key found
            return mid;
        }
    }
}
