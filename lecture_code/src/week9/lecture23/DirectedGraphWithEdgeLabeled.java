package week9.lecture23;

import java.util.HashMap;
import java.util.Map;

public class DirectedGraphWithEdgeLabeled {
    private final int V;
    private Map<Integer, Integer>[] adj;
    private int edge;

    /**
     * Create empty graph with V vertices
     * @param V qty of vertices
     */
    public DirectedGraphWithEdgeLabeled(int V) {
        this.V = V;
        adj = (Map<Integer, Integer>[]) new Map[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new HashMap<>();
        }
        edge = 0;
    }

    /**
     * add an edge v -> w
     */
    public void addEdge(int v, int w, int distance) {
        adj[v].put(w, distance);
        edge += 1;
    }

    /**
     * vertices adjacent to v
     */
    public Map<Integer, Integer> adj(int v) {return adj[v];}

    /**
     * number of vertices
     */
    public int V() {return V;}

    /**
     * number of edges
     */
    public int E() {return edge;}

    /**
     *
     */
    public static void print(DirectedGraphWithEdgeLabeled g) {
        for (int v = 0; v < g.V(); v++) {
            for (int w : g.adj(v).keySet()) {
                System.out.println(v + " -> " + w + " (" + g.adj(v).get(w) + ")");
            }
        }
    }

    public static void main(String[] args) {
        DirectedGraphWithEdgeLabeled g = new DirectedGraphWithEdgeLabeled(7);
        g.addEdge(0, 1, 2);
        g.addEdge(0, 2, 1);
        g.addEdge(1, 2, 5);
        g.addEdge(1, 3, 11);
        g.addEdge(1, 4, 3);
        g.addEdge(4, 2, 1);
        g.addEdge(2, 5, 15);
        g.addEdge(6, 3, 1);
        g.addEdge(6, 5, 1);
        g.addEdge(3, 4, 2);
        g.addEdge(4, 6, 5);
        g.addEdge(4, 5, 4);
        print(g);
    }
}
