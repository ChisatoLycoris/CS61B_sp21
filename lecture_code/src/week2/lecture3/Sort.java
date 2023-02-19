package week2.lecture3;

public class Sort {
	
	// Sorts the Strings destructively
    // Selection Sort
	public static void sort(String[] x) {
		// Find the smallest item
		// move it to the front
		// Selection sort the remaining N - 1 items (without touching front item)
		sort(x, 0);
		
	}
	
	//  Sort x starting at position start
	public static void sort(String[] x, int start) {
		if (start == x.length) {
			return;
		}
		int smallestIndex = findSmallest(x, start);
		swap(x, start, smallestIndex);
		sort(x, start + 1);
	}
	
	// Return the index of the smallest String in x, starting at start
	public static int findSmallest(String[] x, int start) {
		int smallestIndex = start;
		for (int i = start; i < x.length; i++) {
			if (x[i].compareTo(x[smallestIndex]) < 0 ) {
				smallestIndex = i;
			}
		}
		return smallestIndex;
	}
	
	// Swap item a with b
	public static void swap(String[] x, int a, int b) {
		String temp = x[a];
		x[a] = x[b];
		x[b] = temp;
	}
	
	
	// practice before seeing professor demonstrate
	public static void sortPractice(String[] input) {
		for (int i = 0; i < input.length; i++) {
			String min = input[i];
			int minIndex = i;
			String temp = input[i];
			for (int j = 1; (i + j) < input.length; j++) {
				if (min.compareTo(input[i + j]) > 0) {
					min = input[i + j];
					minIndex = i + j;
				}
			}
			input[i] = min;
			input[minIndex] = temp;
		}
	}
}
