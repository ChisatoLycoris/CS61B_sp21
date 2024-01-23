package week9.lecture25;

import java.util.NoSuchElementException;

public class BinarySearchTree<Key extends Comparable<Key>, Value> implements SearchTree<Key, Value>{
    private Node root;

    /**
     * the number of key-value pairs in this SearchTree.
     */
    @Override
    public int size() {
        return size(root);
    }

    /**
     * Returns true if this SearchTree is empty.
     */
    @Override
    public boolean isEmpty() {
        return SearchTree.super.isEmpty();
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key  key
     */
    @Override
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        return get(root, key);
    }

    private Value get(Node node, Key key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp > 0) {
            return get(node.right, key);
        } else if (cmp < 0) {
            return get(node.left, key);
        } else {
            return node.value;
        }
    }

    /**
     * Inserts the key-value pair into the SearchTree,
     * overwriting the old value with the new value if the key is already in the SearchTree.
     *
     * @param key  key
     * @param value  value
     */
    @Override
    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        if (value == null) {
            delete(key);
            return;
        }
        root = put(root, key, value);
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) return new Node(key, value, 1);
        int cmp = key.compareTo(node.key);
        if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else {
            node.value = value;
        }
        node.size = 1 + size(node.right) + size(node.left);
        return node;
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }

    /**
     * Deletes the key-value pairs by the given key in the SearchTree,
     * and returns the value associated with the given key.
     *
     * @param key  key
     */
    @Override
    public Value delete(Key key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        Value result = get(key);
        if (result == null) return null;
        root = delete(root, key);
        return result;
    }

    private Node delete(Node node, Key key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp > 0) {
            node.right = delete(node.right, key);
        } else if (cmp < 0) {
            node.left = delete(node.left, key);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node temp = node;
                node = min(temp.right);
                node.right = deleteMin(temp.right);
                node.left = temp.left;
            }
        }
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    private Node min(Node node) {
        if (node.left == null) return node;
        else return min(node.left);
    }

    /**
     * Returns the value of ith key from the smallest key in the SearchTree.
     *
     * @param rank  ith
     */
    @Override
    public Value select(int rank) {
        if (rank < 0 || rank >= size()) {
            throw new IndexOutOfBoundsException("invalid argument");
        }
        return select(root, rank);
    }

    private Value select(Node node, int rank) {
        if (rank == size(node.left)) {
            return node.value;
        } else if (rank < size(node.left)) {
            return select(node.left, rank);
        } else {
            return select(node.right, rank - size(node.left) - 1);
        }
    }

    /**
     * Returns the "rank" of key int the SearchTree.
     *
     * @param key  key
     */
    @Override
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        return rank(root, key, 0);
    }

    private int rank(Node node, Key key, int rank) {
        if (node == null) return -1;
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return rank + size(node.left);
        } else if (cmp > 0) {
            return rank(node.right, key, rank + size(node.left) + 1);
        } else {
            return rank(node.left, key, rank);
        }
    }

    /**
     * Returns a SearchTree contains all key-value pairs between from and to.
     *
     * @param from  from
     * @param to  to
     */
    @Override
    public SearchTree<Key, Value> subTree(Key from, Key to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("argument to subTree() is null");
        }
        if (from.compareTo(to) > 0) {
            Key temp = from;
            from = to;
            to = temp;
        }
        return subTree(root, from, to, new BinarySearchTree<>());
    }

    private SearchTree<Key, Value> subTree(Node node, Key from, Key to, SearchTree<Key, Value> subTree) {
        if (node == null) return subTree;
        if (node.key.compareTo(from) < 0) {
            subTree(node.right, from, to, subTree);
        } else if (node.key.compareTo(to) > 0) {
            subTree(node.left, from, to, subTree);
        } else {
            subTree.put(node.key, node.value);
            subTree(node.left, from, to, subTree);
            subTree(node.right, from, to, subTree);
        }
        return subTree;
    }

    /**
     * Returns the value whose key is closest to the given key.
     *
     * @param key  key
     */
    @Override
    public Value nearest(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to nearest() is null");
        if (size() == 0) throw new NoSuchElementException("call nearest() with a empty SearchTree");
        return nearest(root, key, root).value;
    }

    private Node nearest(Node node, Key key, Node best) {
        if (node == null) return best;
        if (distance(node.key, key) < distance(best.key, key)) {
            best = node;
        }
        int cmp = node.key.compareTo(key);
        if (cmp > 0) {
            return nearest(node.left, key, best);
        } else if (cmp < 0) {
            return nearest(node.right, key, best);
        } else {
            return node;
        }
    }

    /**
     * calculate the distance of 2 given key
     * this is just an implementation example
     * real implementation according to the actual situation and the algorithm
     * @param key1  key1
     * @param key2  key2
     * @return distance
     */
    private int distance(Key key1, Key key2) {
        return Math.abs(key1.compareTo(key2));
    }

    private class Node {
        Key key;
        Value value;
        Node left;
        Node right;
        int size;

        Node (Key key, Value value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    public static void main(String[] args) {
        SearchTree<Integer, String> testTree = new BinarySearchTree<>();
        testTree.put(9, "9");
        testTree.put(3, "3");
        testTree.put(6, "6");
        testTree.put(7, "7");
        testTree.put(1, "1");
        testTree.put(2, "2");
        testTree.put(5, "5");
        testTree.put(8, "8");
        testTree.put(4, "4");
        SearchTree<Integer, String> newTree = testTree.subTree(9, 6);
        System.out.println(newTree);
        String test = testTree.nearest(-1);
        System.out.println(test);
    }
}
