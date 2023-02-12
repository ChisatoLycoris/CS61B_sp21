package lecture8;

/*An GenericSLList is a list of Generic Type, or an Object*/
public class SLList<GenericType> implements List61B<GenericType>{

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

	public SLList() {
		sentinel = new StuffNode(null, null);
		size = 0;
	}

	public SLList(GenericType x) {
		sentinel = new StuffNode(null, null);
		sentinel.next = new StuffNode(x, null);
		size = 1;
	}
	
	/* Adds x to the front of the list*/
	@Override
	public void addFirst(GenericType x) {
		sentinel.next = new StuffNode(x, sentinel.next);
		size += 1;
	}
	@Override
	public GenericType getFirst() {
		return sentinel.next.item;
	}

	@Override
	public GenericType getLast() {
		StuffNode target = sentinel.next;
		while (target.next != null) {
			target = target.next;
		}
		return target.item;
	}

	/** Adds an item to the end of the list */
	@Override
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
	@Override
	public int size() {
		return size;
	}

	@Override
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

	@Override
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

	@Override
	public GenericType get(int x) {
		StuffNode target = sentinel;
		if (x < 0 || x >= size) {
			System.out.printf("index no. %d out of bounds", x);
			return null;
		}
		for (int i = 0; i <= x; i++) {
			target = target.next;
		}
		return target.item;
	}

	@Override
	public void insert(GenericType x, int position) {
		if (position < 0 || position > size) {
			System.out.printf("Position %d out of bounds of length %d%n", position, size);
			return;
		}
		StuffNode insert = new StuffNode(x, null);
		StuffNode target = sentinel;
		for (int i = 0; i < position; i++) {
			target = target.next;
		}
		insert.next = target.next;
		target.next = insert;
	}

	public static void main(String[] args) {
		/* Create a list of String */
		SLList<String> L = new SLList<>("World");
		L.addFirst("Hello");
		L.addLast("!");
		System.out.println(L.size());
		
	}

}
