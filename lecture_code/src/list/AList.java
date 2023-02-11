package list;
/* Array based List*/

	//          0 1  2 3 4 5 6 7
	// items : [6 9 -1 2 0 0 0 0 ...]
	// size : 4

	/* invariants:
	 * addLast: The next item we want to add, will go into position size
	 * getLast: The item we want to return is in the position size - 1
	 * size: The number of items in the List should be size
	 * */
public class AList<Item> implements List61B<Item>{
	private Item[] items;
	private int size;
	private double usage;
	
	/* Creates an empty List*/
	public AList() {
		items = (Item[]) new Object[100];
		size = 0;
		usage = size / (double)items.length;
	}
	
	/* Resizes the underlying array to the target capacity*/
	private void resize(int capacity) {
		Item[] a = (Item[]) new Object[capacity + 1];
		System.arraycopy(items, 0, a, 0, size);
		items = a;
	}
	
	/* Insert x to the front of the List*/
	@Override
	public void addFirst(Item x) {
		if (size == items.length) {
			resize(size * 2);
		}
		Item[] newItems = (Item[])new Object[items.length];
		newItems[0] = x;
		System.arraycopy(items, 0, newItems, 1, size);
		items = newItems;
		size += 1;
		usage = size / items.length;
	}
	
	/* Returns the item from the front of the List*/
	@Override
	public Item getFirst() {
		return items[0];
	}
	
	/* Insert x into the back of the List*/
	@Override
	public void addLast(Item x) {
		if (size == items.length) {
			resize(size * 2);
		}
		items[size] = x;
		size += 1;
		usage = size / items.length;
	}
	
	/* Returns the item from the back of the List*/
	@Override
	public Item getLast() {
		return items[size - 1];
	}
	
	/* Get the ith item in the List (0 is the front)*/
	@Override
	public Item get(int i) {
		return items[i];
	}
	
	/* Returns the number of items in the List*/
	@Override
	public int size() {
		return size;
	}
	
	/* Deletes item form back of the List and 
	 * returns deleted item*/
	@Override
	public Item removeLast() {
		Item x = getLast();
		/* By nulling out items, java is free to destroy the unneeded image from memory*/
		items[size - 1] = null;
		size -= 1;
		usage = size / items.length;
		/* Resize to save some memory*/
		if (usage < 0.25) {
			resize(size / 2);
		}
		return x;
	}
	
	/*Insert an item to the specific position or index*/
	@Override
	public void insert(Item x, int position) {
		if (position > size || position < 0) {
			System.out.printf("Position %d out of bounds of length %d%n", position, size);
		}
		
		if (size == items.length) {
			resize(size * 2);
		}
		/* 0 1 2 3 4 5 6 7 8 9
		 * 2 2   3 3 3 3 3 3 3 3*/
		Item[] temp = (Item[])new Object[items.length];
		System.arraycopy(items, position, temp, 0, (size - position));
		items[position] = x;
		System.arraycopy(temp, 0, items, (position + 1), (size - position));
		size += 1;
		usage = size / items.length;
	}
	
	public static void main(String[] args) {
		AList<Integer> a = new AList<>();
		for (int i = 0; i < 10; i++) {
			a.addLast(i);
		}
		
		a.insert(77, 5);
		a.addFirst(666);
		
	}
	
}