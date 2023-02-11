package list;
/*An SLList is a list of integers, which hides the terrible truth of the nakedness within*/
/*Singly Linked List*/
public class SLList {
	
	/**Nested classes are useful when a class doesn't stand on its own
	 * and is obvious subordinate to another class.
	 * make the nested class private if other classes should never use the nested class*/
	private static class IntNode {
		public int item;
	    public IntNode next;

	    public IntNode(int i, IntNode n){
	        item = i;
	        next = n;
	    }
	}
	
	/* The first item (if it exists) is at sentinel.next*/
	private IntNode sentinel;
	private int size;
	
	public SLList() {
		sentinel = new IntNode(777, null);
		size = 0;
	}
	
	public SLList(int x) {
		sentinel = new IntNode(777, null);
		sentinel.next = new IntNode(x, null);
		size = 1;
	}
	
	/* Adds x to the front of the list*/
	public void addFirst(int x) {
		sentinel.next = new IntNode(x, sentinel.next);
		size += 1;
	}
	
	public int getFirst() {
		return sentinel.next.item;
	}
	
	/** Adds an item to the end of the list */
	public void addLast(int x) {
		size += 1;
		
		IntNode temp = sentinel;
		
		/* move temp until it reaches the end of the list */
		while (temp.next != null) {
			temp = temp.next;
		}
		temp.next = new IntNode(x, null);
	}
	
//	/** Helper method using by the recursiveSize with no arguments*/
//	private int recursiveSize(IntNode p) {
//		if(p.next == null) {
//			return 1;
//		} else {
//			return 1 + recursiveSize(p.next);
//		}
//	}
//	
//	/** Returns the size of the SLList using Recursive and helper method*/
//	public int recursiveSize() {
//		return recursiveSize(sentinel.next);
//	}
//	
//	/** Returns the size of the SLList using no Recursive*/
//	public int size() {
//		IntNode temp = sentinel.next;
//		int count = 1;
//		while (temp.next != null) {
//			temp = temp.next;
//			count += 1;
//		}
//		return count;
//	}
	
	/* Maintain a special size variable that caches the size of the list
	 * TANSTAAFl, but spreading the work over each add call is a net win in almost any circumstance*/
	public int getSize() {
		return size;
	}
	
	public static void main(String[] args) {
		/* Create a list of one integer, namely 10 */
		SLList L = new SLList(15);
		L.addLast(0);
		L.addFirst(10);
		L.addFirst(5);
		
		System.out.println(L.getSize());
		
	}

}
