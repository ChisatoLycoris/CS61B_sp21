package week6.lecture16;

import java.util.NoSuchElementException;

public class LinkedListStack implements Stack{
    IntNode sentinel;

    public LinkedListStack() {
        sentinel = new IntNode(-1000, null);
    }
    private static class IntNode{
        int item;
        IntNode next;
        private IntNode(int x, IntNode node) {
            item = x;
            next = node;
        }
    }

    @Override
    public void push(int x) {
        sentinel.next = new IntNode(x, sentinel.next);
    }

    @Override
    public int pop() {
        if (sentinel.next == null) {
            throw new NoSuchElementException();
        }
        int result = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        return result;
    }
}
