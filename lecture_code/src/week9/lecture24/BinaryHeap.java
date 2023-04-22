package week9.lecture24;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class BinaryHeap<Item, Priority extends Comparable<Priority>> {
    private Item[] items;
    private Map<Item, Integer> position;
    private Map<Item, Priority> priority;
    private int size;

    public BinaryHeap() {
        items = (Item[]) new Object[8];
        position = new HashMap<>();
        priority = new HashMap<>();
        size = 0;
    }

    public void add(Item x, Priority p) {
        if (position.containsKey(x)) throw new IllegalArgumentException("Item already exist in the BinaryHeap.");
        if (p == null) throw new IllegalArgumentException("Priority cannot be null");
        items[size] = x;
        position.put(x, size);
        priority.put(x, p);
        promote(size);
        size += 1;

    }

    private void promote(int k) {
        if (priority.get(items[parent(k)]).compareTo(priority.get(items[k])) > 0) {
            swap(k, parent(k));
            promote(parent(k));
        }
    }

    private int parent(int k) { return (k - 1) / 2; }

    private void swap(int i, int j) {
        Item temp = items[i];
        items[i] = items[j];
        items[j] = temp;
        position.put(items[i], i);
        position.put(items[j], j);
    }

    private void resize() {
        if (size <= items.length) { return; }
        Item[] newItems = (Item[]) new Object[size * 2];
        System.arraycopy(items, 0, newItems, 0, items.length);
        items = newItems;
    }

    public Item getSmallest() {
        if (size == 0) throw new NoSuchElementException("call getSmallest() while size is 0");
        return items[0];
    }

    public Item removeSmallest() {
        if (size == 0) throw new NoSuchElementException("call removeSmallest() while size is 0");
        Item result = items[0];
        swap(0, size - 1);
        items[size - 1] = null;
        position.remove(result);
        priority.remove(result);
        size -= 1;
        demote(0);
        return result;
    }

    private void demote(int k) {
        int leftChild = k * 2 + 1;
        int rightChild = k * 2 + 2;
        if (leftChild >= size) return;
        if (rightChild >= size || priority.get(items[leftChild]).compareTo(priority.get(items[rightChild])) < 0) {
            if (priority.get(items[k]).compareTo(priority.get(items[leftChild])) > 0) {
                swap(k, leftChild);
                demote(leftChild);
            }
        } else {
            if (priority.get(items[k]).compareTo(priority.get(items[rightChild])) > 0) {
                swap(k, rightChild);
                demote(rightChild);
            }
        }
    }

    public void changePriority(Item x, Priority p) {
        if (!position.containsKey(x)) throw new NoSuchElementException("BinaryHeap does not contain specified item.");
        if (p == null) throw new IllegalArgumentException("Priority cannot be null");
        int idx = position.get(x);
        Priority prev = priority.get(items[idx]);
        priority.put(items[idx], p);
        if (prev.compareTo(p) > 0) {
            promote(idx);
        } else {
            demote(idx);
        }
    }

    public boolean isEmpty() { return size == 0; }

}
