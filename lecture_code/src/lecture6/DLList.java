package lecture6;
/*Doubly Linked List*/
public class DLList<GenericType> {
	
	/**Nested classes are useful when a class doesn't stand on its own
	 * and is obvious subordinate to another class.
	 * make the nested class private if other classes should never use the nested class*/
	private class StuffNode {
		public GenericType item;
		public StuffNode prev;
	    public StuffNode next;

	    public StuffNode(GenericType i, StuffNode p, StuffNode n){
	        item = i;
	        prev = p;
	        next = n;
	    }
	}
	
	/* The first item (if it exists) is at sentinel.next*/
	private StuffNode sentinel;
	private int size;
	
	public DLList() {
		sentinel = new StuffNode(null, null, null);
		sentinel.next = sentinel;
		sentinel.prev = sentinel;
		size = 0;
	}
	
	public DLList(GenericType x) {
		sentinel = new StuffNode(null, null, null);
		sentinel.next = new StuffNode(x, sentinel, sentinel);
		sentinel.prev = sentinel.next;
		size = 1;
	}
	
	/* Adds x to the front of the list*/
	public void addFirst(GenericType x) {
		StuffNode newNode = new StuffNode(x, sentinel,sentinel.next);
		sentinel.next.prev = newNode;
		sentinel.next = newNode;
		size += 1;
	}
	
	/*Removes the first item of the list*/
	public void removeFirst() {
		if (size > 0) {
			sentinel.next.next.prev = sentinel;
			sentinel.next = sentinel.next.next;
			size -= 1;
			return;
		}
		System.out.println("There is nothing inside the list!");
	}
	
	public GenericType getFirst() {
		return sentinel.next.item;
	}
	
	/** Adds an item to the end of the list */
	public void addLast(GenericType x) {
		StuffNode newNode = new StuffNode(x, sentinel.prev, sentinel);
		sentinel.prev.next = newNode;
		sentinel.prev = newNode;
		size += 1;
	}
	
	/*Removes the last item of the list*/
	public void removeLast() {
		if (size > 0) {
			sentinel.prev.prev.next = sentinel;
			sentinel.prev = sentinel.prev.prev;
			size -= 1;
			return;
		}
		System.out.println("There is nothing inside the list!");
	}
	
	public GenericType getLast() {
		return sentinel.prev.item;
	}
	
	/* Helper method to get item from first*/
	private GenericType getFromFirst(int i) {
		StuffNode temp = sentinel;
		for (int j = 0; j <= i; j++) {
			temp = temp.next;
		}
		return temp.item;
	}
	
	/* Helper method to get item from last*/
	private GenericType getFromLast(int i ) {
		StuffNode temp = sentinel;
		for (int j = size - 1; j >= i; j--) {
			temp = temp.prev;
		}
		return temp.item;
	}
	
	/* Returns the ith item in the List*/
	public GenericType get(int i) {
		if (i > -1 && i < size) {
			if ((i - 0) < (size - i)) {
				return getFromFirst(i);
			} else {
				return getFromLast(i);
			}
		}
		System.out.printf("Index %d out of bounds for length %d%n", i, size);
		return null;
	}
	
	/* Maintain a special size variable that caches the size of the list
	 * TANSTAAFl, but spreading the work over each add call is a net win in almost any circumstance*/
	public int getSize() {
		return size;
	}
	
	public static void main(String[] args) {
		/* Create a list of String */
		DLList<String> L = new DLList<>();
		L.removeFirst();
		L.addFirst("World");
		L.addFirst("Hello");
		L.addLast("!");
		L.addLast("?");
		L.addLast("last");
		System.out.println(L.get(0));
		System.out.println(L.get(1));
		System.out.println(L.get(2));
		System.out.println(L.get(3));
		System.out.println(L.get(4));
		System.out.println(L.get(5));
		
	}

}
