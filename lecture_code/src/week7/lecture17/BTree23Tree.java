package week7.lecture17;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class BTree23Tree<Key extends Comparable<Key>, Value> {
    private Node root;
    private int itemsQty;

    public BTree23Tree(int itemsQty) {
        this.itemsQty = itemsQty;
    }

    private class Node {
        private List<ItemMapping> itemList;
        private List<Node> nodeList;
        private int size;
        private boolean isLeaf;
        private Node(Key key, Value value) {
            itemList = new LinkedList<>();
            itemList.add(new ItemMapping(key, value));
            size = 1;
            nodeList = new ArrayList<>();
            for (int i = 0; i <= itemsQty; i++) {
                nodeList.add(null);
            }
            isLeaf = true;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("(");
            for (ItemMapping item : itemList) {
                sb.append(item.key.toString()).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append(")");
            return sb.toString();
        }
    }

    private class ItemMapping {
        private final Key key;
        private Value value;
        private ItemMapping(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
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
        for (int i = 0; i < x.itemList.size(); i++) {
            int cmp = key.compareTo(x.itemList.get(i).key);
            if (cmp < 0) {
                return get(x.nodeList.get(i), key);
            }
            if (cmp == 0) {
                return x.itemList.get(i).value;
            }
        }
        return get(x.nodeList.get(x.nodeList.size() - 1), key);
    }

    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }
        if (value == null) {
            delete(key);
            return;
        }
        root = spilt(put(root, key, value));
    }

    private Node put(Node x, Key key, Value value) {
        if (x == null) {
            return new Node(key, value);
        }
        if (x.isLeaf) {
            x.itemList.add(new ItemMapping(key, value));
            x.itemList.sort(Comparator.comparing((ItemMapping item) -> item.key));
            return x;
        }
        for (int i = 0; i < x.itemList.size(); i++) {
            int cmp = key.compareTo(x.itemList.get(i).key);
            if (cmp < 0) {
                Node temp = x.nodeList.remove(i);
                Node result = put(temp, key, value);
                x.nodeList.add(i, result);
                spilt(x, result);
                return x;
            }
            if (cmp == 0) {
                x.itemList.get(i).value = value;
                return x;
            }
        }
        Node temp = x.nodeList.remove(x.itemList.size());
        Node result = put(temp, key, value);
        x.nodeList.add(x.itemList.size(), result);
        spilt(x, result);
        return x;
    }

    private void spilt(Node parent, Node x) {
        if (x.itemList.size() <= itemsQty) {
            return;
        }
        ItemMapping moveUpItem = x.itemList.remove(1);
        ItemMapping spiltItem = x.itemList.remove(0);
        parent.itemList.add(moveUpItem);
        parent.itemList.sort(Comparator.comparing((ItemMapping item) -> item.key));
        Node spilt = new Node(spiltItem.key, spiltItem.value);
        spilt.nodeList.remove(1);
        spilt.nodeList.add(1, x.nodeList.remove(1));
        spilt.nodeList.remove(0);
        spilt.nodeList.add(0, x.nodeList.remove(0));
        spilt.size = 1 + size(spilt.nodeList.get(0)) + size(spilt.nodeList.get(1));
        spilt.isLeaf = (spilt.nodeList.get(0) == null) && (spilt.nodeList.get(1) == null);
        x.nodeList.add(null);
        if (x.nodeList.size() < itemsQty + 1) {
            x.nodeList.add(null);
        }
        x.size = x.itemList.size();
        x.nodeList.forEach((Node temp) -> x.size += size(temp));
        int moveUpIndex = parent.itemList.indexOf(moveUpItem);
        parent.nodeList.add(moveUpIndex, spilt);
        if (parent.nodeList.get(itemsQty + 1) == null) {
            parent.nodeList.remove(itemsQty + 1);
        }
    }

    private Node spilt(Node x) {
        if (x.itemList.size() <= itemsQty) {
            return x;
        }
        ItemMapping moveUpItem = x.itemList.remove(1);
        ItemMapping spiltItem = x.itemList.remove(0);
        Node moveUp = new Node(moveUpItem.key, moveUpItem.value);
        Node spilt = new Node(spiltItem.key, spiltItem.value);
        spilt.nodeList.remove(1);
        spilt.nodeList.add(1, x.nodeList.remove(1));
        spilt.nodeList.remove(0);
        spilt.nodeList.add(0, x.nodeList.remove(0));
        spilt.size = 1 + size(spilt.nodeList.get(0)) + size(spilt.nodeList.get(1));
        spilt.isLeaf = (spilt.nodeList.get(0) == null) && (spilt.nodeList.get(1) == null);
        x.nodeList.add(null);
        if (x.nodeList.size() < itemsQty + 1) {
            x.nodeList.add(null);
        }
        x.size = x.itemList.size();
        x.nodeList.forEach((Node temp) -> x.size += size(temp));
        moveUp.nodeList.remove(0);
        moveUp.nodeList.add(0, spilt);
        moveUp.nodeList.remove(1);
        moveUp.nodeList.add(1, x);
        moveUp.isLeaf = false;
        moveUp.size = 1 + spilt.size + x.size;
        return moveUp;
    }

    public void delete(Key key) {

    }

    public void print() {
        List<Key> keyList = new LinkedList<>();
        List<Node> nodeList = new LinkedList<>();
        nodeList.add(root);
        while (!nodeList.isEmpty()) {
            Node current = nodeList.remove(0);
            if (current == null) {
                System.out.println("null");
                continue;
            }
            System.out.println(current);
            if (current.isLeaf) {
                continue;
            }
            nodeList.addAll(current.nodeList);
        }
    }

    public static void main(String[] args) {
        BTree23Tree<Integer, Integer> test = new BTree23Tree<>(2);
        test.put(2, 0);
        test.print();
        System.out.println("=======================");
        test.put(3, 0);
        test.print();
        System.out.println("=======================");
        test.put(4, 0);
        test.print();
        System.out.println("=======================");
        test.put(5, 0);
        test.print();
        System.out.println("=======================");
        test.put(6, 0);
        test.print();
        System.out.println("=======================");
        test.put(1, 0);
        test.print();
        System.out.println("=======================");
        test.put(7, 0);
        test.print();
        System.out.println("=======================");
        test.put(8, 0);
        test.print();
        System.out.println("=======================");
        test.put(9, 0);
        test.print();
        System.out.println("=======================");
        test.put(10, 0);
        test.print();
        System.out.println("=======================");
        test.put(11, 0);
        test.print();
        System.out.println("=======================");
        test.put(12, 0);
        test.print();
        System.out.println("=======================");
    }
}
