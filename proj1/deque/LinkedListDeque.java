package deque;

import java.util.Iterator;

/**
 * A Deque (Double-ended queue) implementation class in LinkedList data structure
 * */
public class LinkedListDeque<T> implements Deque<T>, Iterable<T>{
    private int size;
    private Node<T> sentinel;

    public LinkedListDeque() {
        size = 0;
        Node<T> init = new Node<>();
        init.prev = init;
        init.next = init;
        sentinel = init;
    }

    public LinkedListDeque(T item) {
        size = 1;
        sentinel = new Node<T>();
        Node<T> first = new Node<>(item, sentinel, sentinel);
        sentinel.next = first;
        sentinel.prev = first;
    }

    @Override
    public void addFirst(T item) {
        size += 1;
        Node<T> first = new Node<>(item, sentinel.next, sentinel);
        sentinel.next.prev = first;
        sentinel.next = first;
    }

    @Override
    public void addLast(T item) {
        size += 1;
        Node<T> last = new Node<>(item, sentinel, sentinel.prev);
        sentinel.prev.next = last;
        sentinel.prev = last;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        StringBuilder printDeque = new StringBuilder("[");
        Node<T> printTarget = sentinel.next;
        for (int i = 0; i < size; i++) {
            printDeque.append(printTarget.item);
            printDeque.append(", ");
            printTarget = printTarget.next;
        }
        printDeque.delete(printDeque.length() - 2, printDeque.length());
        printDeque.append("]");
        System.out.println(printDeque);
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            System.out.println("LinkedListDeque is empty, nothing can be removed.");
            return null;
        }
        size -= 1;
        T item = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        return item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            System.out.println("LinkedListDeque is empty, nothing can be removed.");
            return null;
        }
        size -= 1;
        T item = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        return item;
    }

    /**
     *
     * */
    @Override
    public T get(int index) {
        if (index > size - 1 || index < 0) {
            System.out.println("Index out of bounds of LinkedListDeque");
            return null;
        }
        Node<T> target = sentinel;
        if (index <= (size / 2)) {
            for (int i = 0; i <= index; i++) {
                target = target.next;
            }
        } else {
            for (int i = size - 1; i >= index; i--) {
                target = target.prev;
            }
        }
        return target.item;
    }

    public T getRecursive(int index) {
        return getRecursive(index, sentinel.next);
    }

    private T getRecursive(int index, Node<T> start) {
        if (index == 0) {
            return start.item;
        }
        return getRecursive(index - 1, start.next);
    }

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node() {

        }
        Node(T item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedLiseDequeIterator();
    }


    private class LinkedLiseDequeIterator implements Iterator<T> {
        private Node<T> current;

        private LinkedLiseDequeIterator() {
            current = sentinel;
        }
        @Override
        public boolean hasNext() {
            return !current.next.equals(sentinel);
        }

        @Override
        public T next() {
            current = current.next;
            return current.item;
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
//        if (!(o instanceof LinkedListDeque linkedListDequeO)) {return false;}
        if (!(o instanceof LinkedListDeque)) {
            return false;
        }
        LinkedListDeque linkedListDequeO = (LinkedListDeque)o;
        if (this.size != linkedListDequeO.size) {
            return false;
        }
        Node<T> current = this.sentinel;
        Node<T> target = linkedListDequeO.sentinel;
        while (! current.next.equals(this.sentinel)) {
            current = current.next;
            target = target.next;
            if (! current.item.equals(target.item)) {
                return false;
            }
        }
        return true;
    }
}
