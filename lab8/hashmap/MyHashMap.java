package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Ming
 */
public class MyHashMap<K, V> implements Map61B<K, V> {


    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int initialSize;
    private int items;
    private double loadFactor;
    private double maxLoad;

    /** Constructors */
    public MyHashMap() {
        initialSize = 16;
        maxLoad = 0.75;
        items = 0;
        loadFactor = 0;
        buckets = createTable(initialSize);
    }

    public MyHashMap(int initialSize) {
        this.initialSize = initialSize;
        maxLoad = 0.75;
        items = 0;
        loadFactor = 0;
        buckets = createTable(initialSize);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.initialSize = initialSize;
        this.maxLoad = maxLoad;
        items = 0;
        loadFactor = 0;
        buckets = createTable(initialSize);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        buckets = createTable(initialSize);
        items = 0;
        loadFactor = 0;
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        int index = Math.floorMod(key.hashCode(), buckets.length);
        Collection<Node> result = buckets[index];
        if (result == null) {
            return false;
        }
        for (Node node : result) {
            if (key.equals(node.key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        int index = Math.floorMod(key.hashCode(), buckets.length);
        Collection<Node> result = buckets[index];
        if (result == null) {
            return null;
        }
        for (Node node : result) {
            if (key.equals(node.key)) {
                return node.value;
            }
        }
        return null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return items;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        Node node = createNode(key, value);
        add(node, buckets);
        resize();
    }

    /**
     * private helper method to add a node in the buckets
     * @param node
     * @param buckets
     */
    private void add(Node node, Collection<Node>[] buckets) {
        int index = Math.floorMod(node.key.hashCode(), buckets.length);
        Collection<Node> result = buckets[index];
        if (result == null) {
            result = createBucket();
            result.add(node);
            buckets[index] = result;
            items += 1;
            return;
        }
        for (Node inBucket : result) {
            if (node.key.equals(inBucket.key)) {
                inBucket.value = node.value;
                return;
            }
        }
        result.add(node);
        items += 1;
    }

    /**
     * private helper method for resize
     * not resize down
     */
    private void resize() {
        loadFactor = items / (double) buckets.length;
        if (loadFactor < maxLoad) {
            return;
        }
        items = 0;
        Collection<Node>[] newBuckets = createTable(buckets.length * 2);
        for (Collection<Node> bucket : buckets) {
            if (bucket == null) {
                continue;
            }
            for (Node node : bucket) {
                add(node, newBuckets);
            }
        }
        buckets = newBuckets;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (Collection<Node> bucket : buckets) {
            if (bucket == null) {
                continue;
            }
            for (Node node : bucket) {
                keySet.add(node.key);
            }
        }
        return keySet;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        int index = Math.floorMod(key.hashCode(), buckets.length);
        Collection<Node> result = buckets[index];
        if (result == null) {
            return null;
        }
        for (Node node : result) {
            if (key.equals(node.key)) {
                result.remove(node);
                return node.value;
            }
        }
        return null;
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     *
     * @param key
     * @param value
     */
    @Override
    public V remove(K key, V value) {
        int index = Math.floorMod(key.hashCode(), buckets.length);
        Collection<Node> result = buckets[index];
        if (result == null) {
            return null;
        }
        for (Node node : result) {
            if (key.equals(node.key) && value.equals(node.value)) {
                result.remove(node);
                return value;
            }
        }
        return null;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

}
