package week8.lecture22;

import edu.princeton.cs.algs4.Queue;

public class BreadthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private int s;

    public BreadthFirstPaths(Graph G, int s) {
        this.s = s;
        int numberOfVertices = G.V();
        marked = new boolean[numberOfVertices];
        edgeTo = new int[numberOfVertices];
        for (int v = 0; v < numberOfVertices; v++) {
            edgeTo[v] = -1;
        }
        bfs(G, s);
    }

    private void bfs(Graph G, int s) {
        Queue<Integer> fringe = new Queue<>();
        fringe.enqueue(s);
        marked[s] = true;
        while (!fringe.isEmpty()) {
            int v = fringe.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    fringe.enqueue(w);
                    marked[w] = true;
                    edgeTo[w] = v;
                }
            }
        }
    }
}
