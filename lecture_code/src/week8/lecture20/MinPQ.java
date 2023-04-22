package week8.lecture20;

/**
 * (Min) Priority Queue: Allowing tracking and removal of the smallest item in a priority queue
 */
public interface MinPQ<Item extends Comparable<Item>> {
    /** Adds the items to the priority queue. */
    void add(Item x);

    /** Returns the smallest item in the priority queue. */
    Item getSmallest();

    /** Removes the smallest item in the priority queue. */
    Item removeSmallest();

    /** Returns the size of priority queue. */
    int size();

    default boolean isEmpty() { return size() == 0; }
}
