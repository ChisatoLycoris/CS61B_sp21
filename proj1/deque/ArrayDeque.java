package deque;

public class ArrayDeque<T> implements Deque<T>{
    private T[] items;
    private int size;
    private int startIndex;
    private double usage;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        startIndex = 4;
        usage = size / (double)items.length;
    }

    public ArrayDeque(T item) {
        items = (T[]) new Object[8];
        items[3] = item;
        size = 1;
        startIndex = 3;
        usage = size / (double)items.length;
    }

    @Override
    public void addFirst(T item) {
        startIndex -= 1;
        items[startIndex] = item;
        size += 1;
        usage = size / (double)items.length;
        resize();
    }

    @Override
    public void addLast(T item) {
        items[startIndex + size] = item;
        size += 1;
        usage = size / (double)items.length;
        resize();
    }

    /**
     * Resizes the underlying array to the target capacity
     * */
    private void resize() {
        T[] newItems;
        if(usage < 0.25 && size > 15) {
            newItems = (T[]) new Object[items.length / 2];
        } else if (usage >= 0.5) {
            newItems = (T[]) new Object[items.length * 2];
        } else {
            return;
        }
        int newStartIndex = (newItems.length - size) / 2;
        System.arraycopy(items, startIndex, newItems, newStartIndex, size);
        items = newItems;
        startIndex = newStartIndex;
        usage = size / (double)items.length;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        StringBuilder printDeque = new StringBuilder("[");
        for (int i = startIndex; i < startIndex + size; i ++) {
            printDeque.append(items[i]);
            printDeque.append(", ");
        }
        printDeque.delete(printDeque.length() - 2, printDeque.length());
        printDeque.append("]");
        System.out.println(printDeque);
    }

    @Override
    public T removeFirst() {
        if (size == 0) return null;
        T result = items[startIndex];
        items[startIndex] = null;
        startIndex += 1;
        size -= 1;
        usage = size / (double)items.length;
        resize();
        return result;
    }

    @Override
    public T removeLast() {
        if (size == 0) return null;
        int lastIndex = startIndex + size - 1;
        T result = items[lastIndex];
        items[lastIndex] = null;
        size -= 1;
        usage = size / (double)items.length;
        resize();
        return result;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            System.out.println("Index out of bounds of ArrayDeque");
            return null;
        }
        return items[startIndex + index];
    }
}
