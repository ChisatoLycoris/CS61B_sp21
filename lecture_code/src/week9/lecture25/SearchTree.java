package week9.lecture25;

public interface SearchTree<Key extends Comparable<Key>, Value> {
    /** the number of key-value pairs in this SearchTree. */
    int size();

    /** Returns true if this SearchTree is empty. */
    default boolean isEmpty() { return size() == 0; }

    /** Returns the value associated with the given key. */
    Value get(Key key);

    /** Inserts the key-value pair into the SearchTree,
     * overwriting the old value with the new value if the key is already in the SearchTree. */
    void put(Key key, Value value);

    /** Deletes the key-value pairs by the given key in the SearchTree,
     * and returns the value associated with the given key. */
    Value delete(Key key);

    /** Returns the value of ith smallest key in the SearchTree. */
    Value select(int i);

    /** Returns the "rank" of key int the SearchTree. */
    int rank(Key key);

    /** Returns a SearchTree contains all key-value pairs between from and to. */
    SearchTree<Key, Value> subTree(Key from, Key to);

    /** Returns the value whose key is closest to the given key. */
    Value nearest(Key key);
}
