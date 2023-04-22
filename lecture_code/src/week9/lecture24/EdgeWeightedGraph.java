package week9.lecture24;

import java.util.*;

public class EdgeWeightedGraph {
    private int V;
    private List<Edge>[] adj;

    private List<Edge> edges;
    private int edge;

    public EdgeWeightedGraph(int V) {
        this.V = V;
        adj = new List[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new LinkedList<>();
        }
        edges = new LinkedList<>();
        edge = 0;
    }

    public void addEdge(int v, int w, int distance) {
        Edge e = new Edge(v, w, distance);
        adj[v].add(e);
        adj[w].add(e);
        edges.add(e);
        edge += 1;
    }

    public List<Edge> adj(int v) { return adj[v]; }

    public List<Edge> edges() { return edges; }

    public int V() { return V; }

    public int E() { return edge; }

    public static void print(EdgeWeightedGraph g) {
        for (Edge e : g.edges()) {
            System.out.println(e);
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
        print(g);
    }
}
