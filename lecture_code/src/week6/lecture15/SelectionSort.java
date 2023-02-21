package week6.lecture15;

public class SelectionSort {

    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length; i ++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j ++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
        }
    }

    public static void main(String[] args) {
        int[] a = {3, 5, 2, 8, 7, 4, 1, 6, 9, 0};
        selectionSort(a);
        for (int i : a) {
            System.out.print(i + " ");
        }
    }
}
