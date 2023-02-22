package bstmap;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    private BSTNode sentinel;
    private int size;

    public BSTMap() {
        size = 0;
    }

    private class BSTNode {
        private BSTNode left;
        private BSTNode right;
        private final K key;
        private V value;

        private BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public void clear() {
        sentinel = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return find(key, sentinel) != null;
    }

    @Override
    public V get(K key) {
        BSTNode target = find(key, sentinel);
        if (target == null) {
            return null;
        }
        return target.value;
    }

    private BSTNode find(K key, BSTNode current) {
        if (current == null) {
            return null;
        }
        if (key.compareTo(current.key) < 0) {
            return find(key, current.left);
        } else if (key.compareTo(current.key) > 0) {
            return find(key, current.right);
        } else {
            return current;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        BSTNode previous = null;
        BSTNode current = sentinel;
        while (current != null) {
            if (key.compareTo(current.key) < 0) {
                previous = current;
                current = current.left;
            } else if (key.compareTo(current.key) > 0) {
                previous = current;
                current = current.right;
            } else {
                current.value = value;
                return;
            }
        }
        if (previous == null) {
            sentinel = new BSTNode(key, value);
        } else if (key.compareTo(previous.key) < 0) {
            previous.left = new BSTNode(key, value);
        } else {
            previous.right= new BSTNode(key, value);
        }
        size += 1;
    }

    @Override
    public Set<K> keySet() {
        return keySet(sentinel);
    }

    private TreeSet<K> keySet(BSTNode current) {
        TreeSet<K> set = new TreeSet<K>();
        if (current == null) {
            return set;
        }
        set.addAll(keySet(current.left));
        set.add(current.key);
        set.addAll(keySet(current.right));
        return set;
    }

    public void printInOrder() {
        TreeSet<K> keySet = keySet(sentinel);
        for(K key: keySet) {
            System.out.println(key);
        }
    }

    @Override
    public V remove(K key) {
        BSTNode removeNode = find(key, sentinel);
        if (removeNode == null) {
            return null;
        }
        size -= 1;
        sentinel = remove(key, sentinel);
        return removeNode.value;
    }

    private BSTNode remove(K key, BSTNode current) {
        if (current == null) {
            return null;
        }
        int cmp = key.compareTo(current.key);
        if (cmp < 0) {
            current.left = remove(key, current.left);
        } else if (cmp > 0) {
            current.right = remove(key, current.right);
        } else {
            if (current.right == null) {
                return current.left;
            }
            if (current.left == null) {
                return current.right;
            }
            BSTNode temp = current;
            current = current.right;
            while(current.left != null) {
                current = current.left;
            }
            current.right = removeMin(temp.right);
            current.left = temp.left;
        }
        return current;
    }

    private BSTNode removeMin(BSTNode current) {
        if (current.left == null) {
            return current.right;
        }
        current.left = removeMin(current.left);
        return current;
    }

    @Override
    public V remove(K key, V value) {
        BSTNode removeNode = find(key, sentinel);
        if (removeNode == null || !value.equals(removeNode.value)) {
            return null;
        }
        size -= 1;
        sentinel = remove(key, sentinel);
        return removeNode.value;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
