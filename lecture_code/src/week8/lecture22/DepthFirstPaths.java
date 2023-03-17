package week8.lecture22;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DepthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private int s;

    public DepthFirstPaths(Graph G, int s) {
        this.s = s;
        int numberOfVertices = G.V();
        marked = new boolean[numberOfVertices];
        edgeTo = new int[numberOfVertices];
        for (int v = 0; v < numberOfVertices; v++) {
            edgeTo[v] = -1;
        }
        dfs(G, s);
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    public Iterable<Integer> pathTo(int v) {
        if (!marked[v]) {
            return null;
        }
        List<Integer> path = new ArrayList<>();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.add(x);
        }
        path.add(s);
        Collections.reverse(path);
        return path;
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }
}
