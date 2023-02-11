package list;
/*An GenericSLList is a list of Generic Type, or an Object*/
public class GenericSLList<GenericType> {
	
	/**Nested classes are useful when a class doesn't stand on its own
	 * and is obvious subordinate to another class.
	 * make the nested class private if other classes should never use the nested class*/
	private class StuffNode {
		public GenericType item;
	    public StuffNode next;

	    public StuffNode(GenericType i, StuffNode n){
	        item = i;
	        next = n;
	    }
	}
	
	/* The first item (if it exists) is at sentinel.next*/
	private StuffNode sentinel;
	private int size;
	
	public GenericSLList() {
		sentinel = new StuffNode(null, null);
		size = 0;
	}
	
	public GenericSLList(GenericType x) {
		sentinel = new StuffNode(null, null);
		sentinel.next = new StuffNode(x, null);
		size = 1;
	}
	
	/* Adds x to the front of the list*/
	public void addFirst(GenericType x) {
		sentinel.next = new StuffNode(x, sentinel.next);
		size += 1;
	}
	
	public GenericType getFirst() {
		return sentinel.next.item;
	}
	
	/** Adds an item to the end of the list */
	public void addLast(GenericType x) {
		size += 1;
		
		StuffNode temp = sentinel;
		
		/* move temp until it reaches the end of the list */
		while (temp.next != null) {
			temp = temp.next;
		}
		temp.next = new StuffNode(x, null);
	}
	
//	/** Helper method using by the recursiveSize with no arguments*/
//	private int recursiveSize(StuffNode p) {
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
	public int size() {
		return size;
	}
	
	public void print() {
		System.out.println("The boss doesn't know what he's doing");
		for (StuffNode p = sentinel.next; p != null; p = p.next) {
			System.out.print(p.item + " ");
		}
		System.out.println();
	}

	public GenericType removeFirst() {
		if (sentinel.next == null) {
			return null;
		}
		GenericType first = sentinel.next.item;
		sentinel.next = sentinel.next.next;
		return first;
	}

	public GenericType removeLast() {
		if (sentinel.next == null) {
			return null;
		}
		StuffNode target = sentinel;
		while (target.next.next != null) {
			target = target.next;
		}
		GenericType last = target.next.item;
		target.next = null;
		return last;
	}
	
	public static void main(String[] args) {
		/* Create a list of String */
		GenericSLList<String> L = new GenericSLList<>("World");
		L.addFirst("Hello");
		L.addLast("!");
		System.out.println(L.size());
		
	}

}
