package week8.lecture21;

import java.util.LinkedList;
import java.util.List;

public class GraphTraversals<T> {

    class Node {
        T key;
        List<Node> link;
        Node(T key) {
            this.key = key;
            link = new LinkedList<>();
        }
    }

    public Node createVertices(T key) {
        return new Node(key);
    }

    public void connect(Node a, Node b) {
        if (!a.link.contains(b)) {
            a.link.add(b);
        }
        if (!b.link.contains(a)) {
            b.link.add(a);
        }
    }

    public boolean isConnected(Node s, Node t) {
        List<Node> marked = new LinkedList<>();
        return isConnected(s, t, marked);
    }
    public boolean isConnected(Node s, Node t, List<Node> marked) {
        if (s == t) {
            return true;
        }
        marked.add(s);
        for (Node x : s.link) {
            if (marked.contains(x)) {
                continue;
            }
            if (isConnected(x, t, marked)) {
                return true;
            }
        }
        return false;
    }

    public void depthFirstSearchPreorder(Node s) {
        List<Node> marked = new LinkedList<>();
        depthFirstSearchPreorder(s, marked);
    }

    private void depthFirstSearchPreorder(Node s, List<Node> marked) {
        marked.add(s);
        System.out.print(s.key + " ");
        for (Node x : s.link) {
            if (marked.contains(x)) {
                continue;
            }
            depthFirstSearchPreorder(x, marked);
        }
    }

    public void depthFirstSearchPostorder(Node s) {
        List<Node> marked = new LinkedList<>();
        depthFirstSearchPostorder(s, marked);
    }

    private void depthFirstSearchPostorder(Node s, List<Node> marked) {
        marked.add(s);
        for (Node x : s.link) {
            if (marked.contains(x)) {
                continue;
            }
            depthFirstSearchPostorder(x, marked);
        }
        System.out.print(s.key + " ");
    }

    public void breadthFirstSearch(Node s) {
        List<Node> marked = new LinkedList<>();
        marked.add(s);
        int i = 0;
        while (i < marked.size()) {
            System.out.print(marked.get(i).key + " ");
            for (Node x : marked.get(i).link) {
                if (!marked.contains(x)) {
                    marked.add(x);
                }
            }
            i += 1;
        }
    }

    public static void main(String[] args) {
        GraphTraversals<Integer> gt = new GraphTraversals<>();
        GraphTraversals<Integer>.Node a = gt.createVertices(0);
        GraphTraversals<Integer>.Node b = gt.createVertices(1);
        GraphTraversals<Integer>.Node c = gt.createVertices(2);
        GraphTraversals<Integer>.Node d = gt.createVertices(3);
        GraphTraversals<Integer>.Node e = gt.createVertices(4);
        GraphTraversals<Integer>.Node f = gt.createVertices(5);
        GraphTraversals<Integer>.Node g = gt.createVertices(6);
        GraphTraversals<Integer>.Node h = gt.createVertices(7);
        GraphTraversals<Integer>.Node i = gt.createVertices(8);
        gt.connect(a, b);
        gt.connect(b, c);
        gt.connect(b, e);
        gt.connect(d, e);
        gt.connect(c, f);
        gt.connect(e, f);
        gt.connect(f, g);
        gt.connect(f, i);
        gt.connect(g, h);
        System.out.println(gt.isConnected(a, h));
        gt.depthFirstSearchPreorder(a);
        System.out.println();
        gt.depthFirstSearchPostorder(a);
        System.out.println();
        gt.breadthFirstSearch(a);
    }
}
