package discussion3;

import lecture4.IntList;

public class Practice_EvenOdd {
	
	public static void evenOdd1(IntList lst) {
		if (lst == null || lst.rest == null) {
			return;
		}
		
		IntList second = lst.rest;
		IntList odd = lst.rest;
		while (odd != null && odd.rest != null) {
			lst.rest = lst.rest.rest;
			odd.rest = odd.rest.rest;
			lst = lst.rest;
			odd = odd.rest;
		}
		lst.rest = second;
	}
	
	
	public static void evenOdd2(IntList lst) {
		if (lst == null || lst.rest == null || lst.rest.rest == null) {
			return;
		}
		
		IntList second = lst.rest;
		int index = 0;
		while (!(index % 2 == 0 && (lst.rest == null || lst.rest.rest == null))) {
			IntList temp = lst.rest;
			lst.rest = lst.rest.rest;
			lst = temp;
			index++;
		}
		lst.rest = second;
	}
//	Explanation: For any linked list, observe that we simply want to change the rest
//	attribute of each IntList instance to skip an IntList instance. Looking at lst, we
//	want to link 0 to 1, 3 to 4, and so on. This will constitute the work of the body of
//	the while loop, so we just to need to figure out how to link the last even indexed
//	IntList instance to the first odd indexed IntList instance. To keep track of the
//	first odd indexed IntList instance, we can use second. Now, we just need to exit
//	the while loop when we are at the last even indexed IntList instance. This occurs
//	when the index is even and we are either at the second to last element (lst.rest.
//	rest == null) or the last element (lst.rest == null).
	
	
	public static void main(String[] args) {
		IntList L = new IntList(6, null);
		L = new IntList(5, L);
		L = new IntList(4, L);
		L = new IntList(3, L);
		L = new IntList(2, L);
		L = new IntList(1, L);
		L = new IntList(0, L);
		
		evenOdd1(L);

	}

}

//2 Even Odd
//Implement the method evenOdd by destructively changing the ordering of a given
//IntList so that even indexed links precede odd indexed links.
//For instance, if lst is defined as IntList.list(0, 3, 1, 4, 2, 5), evenOdd(lst)
//would modify lst to be IntList.list(0, 1, 2, 3, 4, 5).
//You may not need all the lines.
//Hint: Make sure your solution works for lists of odd and even lengths
