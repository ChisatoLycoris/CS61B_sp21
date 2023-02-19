package week3.discussion3;

/**
 * Fill the lower-left triangle of S with elements of LL and the
 * upper-right triangle of S with elements of UR (from left-to
 * right, top-to-bottom in each case). Assumes that S is square and
 * LL and UR have at least sufficient elements.
 * Full question description at the bottom of this java
 * */

public class Practice_FillGrid {

	
	/* solution that I answered, also 1 of the solution that provided by CS61B*/
	public static void fillGrid1(int[] LL, int[] UR, int[][] S) {
		int N = S.length;
		int kL, kR;
		kL = kR = 0;
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < i; j++) {
				S[i][j] = LL[kL];
				kL += 1;
			}
			for (int j = i + 1; j < N; j++) {
				S[i][j] = UR[kR];
				kR += 1;
			}
		}
	}
	
	/* another solution that provided by CS61B*/
	public static void fillGrid2(int[] LL, int[] UR, int[][] S) {
		int N = S.length;
		int kL, kR;
		kL = kR = 0;
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i > j) {
					S[i][j] = LL[kL];
					kL += 1;
				} else if (i < j) {
					S[i][j] = UR[kR];
					kR += 1;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int[] LL = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		int[] UR = {11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
		int[][] S = {
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0}
		};
		fillGrid2(LL, UR, S);
		for (int i = 0; i < S.length; i++) {
			for(int j = 0; j < S[i].length; j++) {
				System.out.printf("%3d", S[i][j]);
			}
			System.out.println();
		}
		
	}
}

//Fill Grid
//Given two one-dimensional arrays LL and UR, fill in the program on the next page
//to insert the elements of LL into the lower-left triangle of a square two-dimensional
//array S and UR into the upper-right triangle of S, without modifying elements along
//the main diagonal of S. You can assume LL and UR both contain at least enough
//elements to fill their respective triangles. (Spring 2020 MT1)
//For example, consider
//int[] LL = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 0 };
//int[] UR = { 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
//int[][] S = {
//	{ 0, 0, 0, 0, 0},
//	{ 0, 0, 0, 0, 0},
//	{ 0, 0, 0, 0, 0},
//	{ 0, 0, 0, 0, 0},
//	{ 0, 0, 0, 0, 0}
//};
//After calling fillGrid(LL, UR, S), S should contain
//{
//	{ 0, 11, 12, 13, 14 },
//	{ 1,  0, 15, 16, 17 },
//	{ 2,  3,  0, 18, 19 },
//	{ 4,  5,  6,  0, 20 },
//	{ 7,  8,  9, 10,  0 }
//}
//(The last two elements of LL are excess and therefore ignored.)
