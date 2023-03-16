package week8.lecture20;

import java.util.NoSuchElementException;

public class HeapMinPQ<Item extends Comparable<Item>> implements MinPQ<Item> {
    private Item[] items;
    private int size;

    public HeapMinPQ() {
        items = (Item[]) new Comparable[8];
        size = 0;
    }

    /**
     * Adds the items to the priority queue.
     */
    @Override
    public void add(Item x) {
        items[size] = x;
        promote(size);
        size += 1;
        resize();
    }

    private void promote(int k) {
        if (items[parent(k)].compareTo(items[k]) > 0) {
            swap(k, parent(k));
            promote(parent(k));
        }
    }

    private int parent(int k) {
        return (k - 1) / 2;
    }

    private void swap(int i, int j) {
        Item temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }

    private void resize() {
        if (size != items.length) {
            return;
        }
        Item[] newItems = (Item[]) new Comparable[items.length * 2];
        System.arraycopy(items, 0, newItems, 0, items.length);
        items = newItems;
    }

    /**
     * Returns the smallest item in the priority queue.
     */
    @Override
    public Item getSmallest() {
        if (size == 0) {
            throw new NoSuchElementException("call getSmallest() while size is 0");
        }
        return items[0];
    }

    /**
     * Removes the smallest item in the priority queue.
     */
    @Override
    public Item removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException("call removeSmallest() while size is 0");
        }
        Item result = items[0];
        swap(0, size - 1);
        items[size - 1] = null;
        size -= 1;
        demote(0);
        return result;
    }

    private void demote(int k) {
        int leftChild = k * 2 + 1;
        int rightChild = k * 2 + 2;
        if (leftChild >= size) {
            return;
        }
        if (rightChild >= size || items[leftChild].compareTo(items[rightChild]) < 0) {
            if (items[k].compareTo(items[leftChild]) > 0) {
                swap(k, leftChild);
                demote(leftChild);
            }
        } else {
            if (items[k].compareTo(items[rightChild]) > 0) {
                swap(k, rightChild);
                demote(rightChild);
            }
        }
    }

    /**
     * Returns the size of priority queue.
     */
    @Override
    public int size() {
        return size;
    }

    public static void main(String[] args) {
        HeapMinPQ<Integer> heap = new HeapMinPQ<>();
        heap.add(5);
        heap.add(7);
        heap.add(3);
        heap.add(5);
        heap.add(10);
        heap.add(9);
        heap.add(1);
        heap.add(3);
        heap.add(0);
        System.out.println(heap.removeSmallest());
        System.out.println(heap.removeSmallest());
        System.out.println(heap.removeSmallest());
        System.out.println(heap.getSmallest());
    }
}
