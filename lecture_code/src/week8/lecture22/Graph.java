package week8.lecture22;

import java.util.ArrayList;
import java.util.List;

/**
 * Graph API from our textbook
 */
public class Graph {
    private final int V;
    private List<Integer>[] adj;
    private int edge;
    /**
     * Create empty graph with V vertices
     */
    public Graph(int V) {
        this.V = V;
        adj = (List<Integer>[]) new ArrayList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<Integer>();
        }
        edge = 0;
    }

    /**
     * add an edge v-w
     */
    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        edge += 1;
    }

    /**
     * vertices adjacent to v
     */
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    /**
     * number of vertices
     */
    public int V() {
        return V;
    }

    /**
     * number of edges
     */
    public int E() {
        return edge;
    }

    public static int degree(Graph G, int v) {
        int degree = 0;
        for (int w : G.adj(v)) {
            degree += 1;
        }
        return degree;
    }

    public static void print(Graph G) {
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                System.out.println(v + "-" + w);
            }
        }
    }
}
