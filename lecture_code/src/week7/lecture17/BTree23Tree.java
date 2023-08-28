package week7.lecture17;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class BTree23Tree<Key extends Comparable<Key>, Value> {
    private Node root;
    private final int itemsQty = 2;

    public BTree23Tree() {
    }

    private class Node {
        private final List<ItemMapping> itemList;
        private final List<Node> nodeList;
        private int size;
        private final boolean isLeaf;
        private Node(Key key, Value value) {
            itemList = new LinkedList<>();
            itemList.add(new ItemMapping(key, value));
            size = 1;
            nodeList = new LinkedList<>();
            isLeaf = true;
        }

        private Node(Key key, Value value, Node left, Node right) {
            itemList = new LinkedList<>();
            itemList.add(new ItemMapping(key, value));
            size = 1 + left.size + right.size;
            nodeList = new LinkedList<>();
            nodeList.add(left);
            nodeList.add(right);
            isLeaf = false;
        }

        private int put(Key key, Value value) {
            int index = 0;
            for (; index < itemList.size(); index++) {
                int cmp = key.compareTo(itemList.get(index).key);
                if (cmp < 0) {
                    break;
                } else if (cmp == 0) {
                    return index;
                }
            }
            itemList.add(index, new ItemMapping(key, value));
            size += 1;
            return index;
        }

        private void calculateSize() {
            int s = itemList.size();
            for (Node node : nodeList) {
                s += node.size;
            }
            size = s;
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
            x.put(key, value);
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
            x.calculateSize();
            parent.calculateSize();
            return;
        }
        ItemMapping moveUpItem = x.itemList.remove(1);
        ItemMapping spiltItem = x.itemList.remove(0);
        int moveUpIndex = parent.put(moveUpItem.key, moveUpItem.value);
        Node spilt;
        if (x.isLeaf) {
            spilt = new Node(spiltItem.key, spiltItem.value);
        } else {
            spilt = new Node(spiltItem.key, spiltItem.value, x.nodeList.remove(0), x.nodeList.remove(0));
        }
        x.calculateSize();
        parent.nodeList.add(moveUpIndex, spilt);
        parent.calculateSize();
    }

    private Node spilt(Node x) {
        if (x.itemList.size() <= itemsQty) {
            x.calculateSize();
            return x;
        }
        ItemMapping moveUpItem = x.itemList.remove(1);
        ItemMapping spiltItem = x.itemList.remove(0);
        Node spilt;
        if (x.isLeaf) {
            spilt = new Node(spiltItem.key, spiltItem.value);
        } else {
            spilt = new Node(spiltItem.key, spiltItem.value, x.nodeList.remove(0), x.nodeList.remove(0));
        }
        x.calculateSize();
        return new Node(moveUpItem.key, moveUpItem.value, spilt, x);
    }

    public void delete(Key key) {
        throw new UnsupportedOperationException();
    }

    public void print() {
        if (size() == 0) return;
        List<Node> nodeList = new LinkedList<>();
        nodeList.add(root);
        while (!nodeList.isEmpty()) {
            Node current = nodeList.remove(0);
            System.out.println(current);
            if (current.isLeaf) {
                continue;
            }
            nodeList.addAll(current.nodeList);
        }
    }

    public static void main(String[] args) {
        BTree23Tree<Integer, Integer> test = new BTree23Tree<>();
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
