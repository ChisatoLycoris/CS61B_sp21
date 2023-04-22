package week9.lecture24;

import week6.lecture14.DisjointSets;
import week6.lecture14.WQNPathCompressionDS;
import week8.lecture20.HeapMinPQ;
import week8.lecture20.MinPQ;

import java.util.ArrayList;
import java.util.List;

public class KruskalMST {
    private List<Edge> mst;

    public KruskalMST(EdgeWeightedGraph G) {
        mst = new ArrayList<>();
        MinPQ<Edge> pq = new HeapMinPQ<>();
        for (Edge e : G.edges()) {
            pq.add(e);
        }
        DisjointSets ds = new WQNPathCompressionDS(G.V());
        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.removeSmallest();
            int v = e.from();
            int w = e.to();
            if (!ds.isConnected(v, w)) {
                ds.connect(v, w);
                mst.add(e);
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
        System.out.println(g.edges());
        KruskalMST kruskalMST = new KruskalMST(g);
        System.out.println(kruskalMST.mst);
    }
}
