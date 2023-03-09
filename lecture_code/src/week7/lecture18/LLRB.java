package week7.lecture18;

/**
 * Left Leaning Red-Black Tree
 */
public class LLRB<Key extends Comparable<Key>, Value> {
    private final static boolean RED = true;
    private final static boolean BLACK = false;

    private Node root;

    private class Node {
        private final Key key;
        private Value value;
        private Node left;
        private Node right;
        private int size;
        private boolean color;
        private Node(Key key, Value value, boolean color) {
            this.key = key;
            this.value = value;
            this.color = color;
            size = 1;
        }
    }

    public LLRB() {

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
        if (key == null || value == null) {
            throw new IllegalArgumentException("calls put() with a null key or value");
        }
        root = put(root, key, value);
        root.color = BLACK;
    }

    private Node put(Node x, Key key, Value value) {
        if (x == null) {
            return new Node(key, value, RED);
        }
        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
            x.right = put(x.right, key, value);
        } else if (cmp < 0) {
            x.left = put(x.left, key, value);
        } else {
            x.value = value;
        }
        x.size = 1 + size(x.right) + size(x.left);
        if (isRed(x.right) && !isRed(x.left)) {
            x = rotateLeft(x);
        }
        if (isRed(x.left) && isRed(x.left.left)) {
            x = rotateRight(x);
        }
        if (isRed(x.left) && isRed(x.right)) {
            flipColors(x);
        }

        return x;
    }

    private boolean isRed(Node x) {
        if (x == null) {
            return false;
        }
        return x.color;
    }

    private Node rotateRight(Node x) {
        assert x != null && isRed(x.left);
        Node temp = x.left;
        x.left = temp.right;
        temp.right = x;
        temp.color = x.color;
        x.color = RED;
        temp.size = x.size;
        x.size = 1 + size(x.left) + size(x.right);
        return temp;
    }

    private Node rotateLeft(Node x) {
        assert x != null && isRed(x.right);
        Node temp = x.right;
        x.right = temp.left;
        temp.left = x;
        temp.color = x.color;
        x.color = RED;
        temp.size = x.size;
        x.size = 1 + size(x.left) + size(x.right);
        return temp;
    }

    private void flipColors(Node x) {
        assert x != null && isRed(x.right) && isRed(x.left);
        x.color = !x.color;
        x.right.color = !x.right.color;
        x.left.color = !x.left.color;
    }
}
