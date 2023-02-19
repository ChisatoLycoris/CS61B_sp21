package week4.lecture9;

/**
 * SLList with additional operation printLostItems() which prints all items
 * that have ever been deleted.
 * */
public class VengefulSLList<Item> extends SLList<Item> {
    SLList<Item> deletedItems;

    public VengefulSLList() {
        super();
        deletedItems = new SLList<Item>();
    }

    public VengefulSLList(Item x) {
        super(x);
        deletedItems = new SLList<Item>();
    }

    @Override
    public Item removeFirst() {
        Item first = super.removeFirst();
        deletedItems.addLast(first);
        return first;
    }

    @Override
    public Item removeLast() {
        Item last = super.removeLast();
        deletedItems.addLast(last);
        return last;
    }

    /** Prints deleted items. */
    public void printLostItems() {
        deletedItems.print();
    }

    public static void main(String[] args) {

        VengefulSLList<Integer> vs1 = new VengefulSLList<>();
        vs1.addLast(1);
        vs1.addLast(5);
        vs1.addLast(10);
        vs1.addLast(13);
        // vs1 is now: [1, 5, 10, 13]

        vs1.removeLast();
        vs1.removeLast();
        // After deletion, vs1 is [1, 5]

        // Should print out the numbers of the fallen, namely 10 and 13.
        System.out.println("The fallen are: ");
        vs1.printLostItems();
    }
}
