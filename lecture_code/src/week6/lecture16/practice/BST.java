package week6.lecture16.practice;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

public class BST<Key extends Comparable<Key>, Value> {
    private Node root;
    private class Node {
        private final Key key;
        private Value value;
        private Node left;
        private Node right;
        private int size;
        private Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            size = 1;
        }
    }

    public BST() {

    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.size;
    }

    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
            return get(x.right, key);
        } else if (cmp < 0) {
            return get(x.left, key);
        } else {
            return x.value;
        }
    }

    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }
        if (value == null) {
            delete(key);
            return;
        }
        root = put(root, key, value);
    }

    private Node put(Node x, Key key, Value value) {
        if (x == null) {
            return new Node(key, value);
        }
        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
            x.right = put(x.right, key, value);
        } else if (cmp < 0) {
            x.left = put(x.left, key, value);
        } else {
            x.value = value;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("calls delete() with a null key");
        }
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
            x.right = delete(x.right, key);
        } else if (cmp < 0) {
            x.left = delete(x.left, key);
        } else {
            if (x.left == null) {
                x = x.right;
            } else if (x.right == null) {
                x = x.left;
            } else {
                Node temp = x;
                x = min(temp.right);
                x.right = deleteMin(temp.right);
                x.left = temp.left;
            }
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    private Node deleteMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("calls min() with empty BST");
        }
        return min(root).key;
    }
    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    private Node deleteMax(Node x) {
        if (x.right == null) {
            return x.left;
        }
        x.right = deleteMax(x.right);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("calss max() with empty BST");
        }
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) {
            return x;
        }
        return max(x.right);
    }

    public Iterable<Key> keys() {
        List<Key> list = new LinkedList<>();
        if (isEmpty()) {
            return list;
        }
        keys(root, list);
        return list;
    }

    private void keys(Node x, List<Key> list) {
        if (x == null) {
            return;
        }
        keys(x.left, list);
        list.add(x.key);
        keys(x.right, list);
    }

    public Iterable<Key> levelOrder() {
        List<Key> keys = new LinkedList<>();
        List<Node> list = new LinkedList<>();
        list.add(root);
        while (!list.isEmpty()) {
            Node x = list.remove(0);
            if (x == null) {
                continue;
            }
            keys.add(x.key);
            list.add(x.left);
            list.add(x.right);
        }
        return keys;
    }
}
