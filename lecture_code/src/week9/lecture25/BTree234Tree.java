package week9.lecture25;

public class BTree234Tree<Key extends Comparable<Key>, Value> implements SearchTree<Key, Value> {
    /**
     * the number of key-value pairs in this SearchTree.
     */
    @Override
    public int size() {
        // TODO:
        return 0;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key
     */
    @Override
    public Value get(Key key) {
        // TODO:
        return null;
    }

    /**
     * Inserts the key-value pair into the SearchTree,
     * overwriting the old value with the new value if the key is already in the SearchTree.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(Key key, Value value) {
        // TODO:
    }

    /**
     * Deletes the key-value pairs by the given key in the SearchTree,
     * and returns the value associated with the given key.
     *
     * @param key
     */
    @Override
    public Value delete(Key key) {
        // TODO:
        return null;
    }

    /**
     * Returns the value of ith smallest key in the SearchTree.
     *
     * @param i
     */
    @Override
    public Value select(int i) {
        // TODO:
        return null;
    }

    /**
     * Returns the "rank" of key int the SearchTree.
     *
     * @param key
     */
    @Override
    public int rank(Key key) {
        // TODO:
        return 0;
    }

    /**
     * Returns a SearchTree contains all key-value pairs between from and to.
     *
     * @param from
     * @param to
     */
    @Override
    public SearchTree<Key, Value> subTree(Key from, Key to) {
        // TODO:
        return null;
    }

    /**
     * Returns the value whose key is closest to the given key.
     *
     * @param key
     */
    @Override
    public Value nearest(Key key) {
        // TODO:
        return null;
    }

    private class Node {

    }
}
