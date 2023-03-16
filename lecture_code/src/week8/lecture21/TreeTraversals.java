package week8.lecture21;

import java.util.LinkedList;
import java.util.List;

public class TreeTraversals<T extends Comparable<T>> {
    private Node root;

    private class Node {
        private T key;
        private Node left;
        private Node right;

        Node(T key) {
            this.key = key;
        }
    }

    public void preorder(Node x) {
        if (x == null) {
            return;
        }
        System.out.print(x.key);
        preorder(x.left);
        preorder(x.right);
    }

    public void inorder(Node x) {
        if (x == null) {
            return;
        }
        inorder(x.left);
        System.out.print(x.key);
        inorder(x.right);
    }

    public void postorder(Node x) {
        if (x == null) {
            return;
        }
        postorder(x.left);
        postorder(x.right);
        System.out.print(x.key);
    }

    public void levelOrder(Node x) {
        if (x == null) {
            return;
        }
        LinkedList<Node> list = new LinkedList<>();
        list.addLast(x);
        while (!list.isEmpty()) {
            Node current = list.removeFirst();
            System.out.print(current.key);
            if (current.left != null) {
                list.addLast(current.left);
            }
            if (current.right != null) {
                list.addLast(current.right);
            }
        }
    }
}
