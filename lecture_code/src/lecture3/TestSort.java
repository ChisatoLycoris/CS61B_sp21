package lecture3;

import org.junit.*;

//	Tests the Sort class
public class TestSort {

	// Test the Sort.sort method 
	@Test
	public void testSort() {
		String[] input = {"i", "have", "an", "egg"};
		String[] expected = {"an", "egg", "have", "i"};
		
		Sort.sort(input);
		
		Assert.assertArrayEquals(expected, input);
	}
	
	//  Test the Sort.findSmallest method
	@Test
	public void testFindSmallest() {
		String[] input = {"i", "have", "an", "egg"};
		int expected = 2;
		
		int actual = Sort.findSmallest(input, 0);
		
		Assert.assertEquals(expected, actual);
		
		String[] input2 = {"there", "are", "many", "pigs"};
		int expected2 = 2;
		
		int actual2 = Sort.findSmallest(input2, 2);
		
		Assert.assertEquals(expected2, actual2);
	}
	
	// Test the Sort.swap method 
	@Test
	public void testSwap() {
		String[] input = {"i", "have", "an", "egg"};
		int a = 0;
		int b = 2;
		String[] expected = {"an", "have", "i", "egg"};
		Sort.swap(input, a, b);
		Assert.assertArrayEquals(expected, input);
	}
	
	//  Test Sort.sortPractice method
	@Test
	public void testSortPractice() {
		String[] input = {"i", "have", "an", "egg"};
		String[] expected = {"an", "egg", "have", "i"};
		
		Sort.sortPractice(input);
		
		Assert.assertArrayEquals(expected, input);
		
		String[] input2 = {"there", "are", "many", "pigs"};
		String[] expected2 = {"are", "many", "pigs", "there"};
		
		Sort.sort(input2);
		
		Assert.assertArrayEquals(expected2, input2);
	}
	

}
