package week6.lecture15;


import java.util.LinkedList;
import java.util.List;

public class MergeSort {

    public static int[] mergeSort(int[] arr) {
        List<int[]> helper = new LinkedList<>();
        for (int i = 0; i < arr.length; i++) {
            int[] item = {arr[i]};
            helper.add(item);
        }
        return mergeSortHelper(helper);
    }

    /*
    private static int[][] spilt2(int[] arr) {
        int[] arr1 = new int[arr.length / 2];
        System.arraycopy(arr, 0, arr1, 0, arr.length / 2);
        int[] arr2 = new int[arr.length - (arr.length / 2)];
        System.arraycopy(arr, arr.length / 2 + 1, arr2, 0, arr.length - (arr.length / 2));
        int[][] result = {arr1, arr2};
        return result;
    }
    */

    private static int[] merge(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length + arr2.length];
        int arr1Index = 0;
        int arr2Index = 0;
        int resultIndex = 0;
        while (arr1Index < arr1.length && arr2Index < arr2.length) {
            if (arr1[arr1Index] < arr2[arr2Index]) {
                result[resultIndex] = arr1[arr1Index];
                arr1Index += 1;
            } else {
                result[resultIndex] = arr2[arr2Index];
                arr2Index += 1;
            }
            resultIndex += 1;
        }
        if (arr1Index == arr1.length) {
            while (arr2Index != arr2.length) {
                result[resultIndex] = arr2[arr2Index];
                arr2Index += 1;
                resultIndex += 1;
            }
        } else {
            while (arr1Index != arr1.length) {
                result[resultIndex] = arr1[arr1Index];
                arr1Index += 1;
                resultIndex += 1;
            }
        }
        return result;
    }

    private static int[] mergeSortHelper(List<int[]> list) {
        int length = list.size();
        if (length == 1) {
            return list.get(0);
        }
        List<int[]> result = new LinkedList<>();
        for (int i = 0; i < length; i += 2) {
            if (i + 1 < length) {
                result.add(merge(list.get(i), list.get(i + 1)));
            } else {
                result.add(list.get(i));
            }
        }
        return mergeSortHelper(result);
    }

    public static void main(String[] args) {
        int[] a = {3, 5, 2, 8, 7, 4, 1, 6, 9, 0};
        int[] result = mergeSort(a);
        for (int i : result) {
            System.out.print(i + " ");
        }
    }
}
