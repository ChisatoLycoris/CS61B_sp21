package week9.lecture24;

import java.util.Arrays;

public class PrimMST {
    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private BinaryHeap<Integer, Double> fringe;

    public PrimMST(EdgeWeightedGraph G, int s) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        fringe = new BinaryHeap<>();

        for (int i = 0; i < G.V(); i++) {
            distTo[i] = Double.MAX_VALUE;
            fringe.add(i, distTo[i]);
        }
        distTo[s] = 0.0;
        fringe.changePriority(s, 0.0);

        while (!fringe.isEmpty()) {
            int v = fringe.removeSmallest();
            scan(G, v);
        }
    }

    private void scan(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (marked[w]) { continue; }
            if (e.weight() < distTo[w]) {
                distTo[w] = e.weight();
                edgeTo[w] = e;
                fringe.changePriority(w, distTo[w]);
            }
        }
    }

    public static void main(String[] args) {
        EdgeWeightedGraph g = new EdgeWeightedGraph(7);
        g.addEdge(0, 1, 2);
        g.addEdge(0, 2, 1);
        g.addEdge(1, 2, 5);
        g.addEdge(1, 3, 11);
        g.addEdge(1, 4, 3);
        g.addEdge(2, 4, 1);
        g.addEdge(2, 5, 15);
        g.addEdge(3, 4, 2);
        g.addEdge(3, 6, 1);
        g.addEdge(4, 5, 4);
        g.addEdge(4, 6, 3);
        g.addEdge(5, 6, 1);
        PrimMST primMST = new PrimMST(g, 0);
        System.out.println(Arrays.toString(primMST.edgeTo));
        System.out.println(Arrays.toString(primMST.distTo));
    }
}
