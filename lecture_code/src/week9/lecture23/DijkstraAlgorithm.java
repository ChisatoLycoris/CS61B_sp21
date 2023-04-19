package week9.lecture23;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class DijkstraAlgorithm {
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;
    private int s;

    public DijkstraAlgorithm(DirectedGraphWithEdgeLabeled G, int s) {
        this.s = s;
        int numberOfVertices = G.V();
        marked = new boolean[numberOfVertices];
        edgeTo = new int[numberOfVertices];
        distTo = new int[numberOfVertices];
        PriorityQueue<TotalDistance> fringe = new PriorityQueue<>();
        for (int v = 0; v < numberOfVertices; v++) {
            edgeTo[v] = -1;
            distTo[v] = Integer.MAX_VALUE;
            fringe.add(new TotalDistance(v, Integer.MAX_VALUE));
        }
        distTo[s] = 0;
        fringe.remove(new TotalDistance(s, Integer.MAX_VALUE));
        fringe.add(new TotalDistance(s, 0));
        bfs(G, s, fringe);
    }

    /**
     * best first search (the shortest first)
     */
    private void bfs(DirectedGraphWithEdgeLabeled G, int v, PriorityQueue<TotalDistance> fringe) {
        marked[v] = true;
        while (!fringe.isEmpty()) {
            TotalDistance cur = fringe.poll();
            marked[cur.v] = true;
            for (int w : G.adj(cur.v).keySet()) {
                int d = distTo[cur.v] + G.adj(cur.v).get(w);
                if (!marked[w] && d < distTo[w]) {
                    fringe.remove(new TotalDistance(w, distTo[w]));
                    fringe.add(new TotalDistance(w, d));
                    distTo[w] = d;
                    edgeTo[w] = cur.v;
                }
            }
        }
    }

    private class TotalDistance implements Comparable<TotalDistance> {
        int v;
        int distance;

        TotalDistance(int v, int distance) {
            this.v = v;
            this.distance = distance;
        }

        /**
         * Compares this object with the specified object for order.  Returns a
         * negative integer, zero, or a positive integer as this object is less
         * than, equal to, or greater than the specified object.
         *
         * <p>The implementor must ensure {@link Integer#signum
         * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
         * all {@code x} and {@code y}.  (This implies that {@code
         * x.compareTo(y)} must throw an exception if and only if {@code
         * y.compareTo(x)} throws an exception.)
         *
         * <p>The implementor must also ensure that the relation is transitive:
         * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
         * {@code x.compareTo(z) > 0}.
         *
         * <p>Finally, the implementor must ensure that {@code
         * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
         * == signum(y.compareTo(z))}, for all {@code z}.
         *
         * @param o the object to be compared.
         * @return a negative integer, zero, or a positive integer as this object
         * is less than, equal to, or greater than the specified object.
         * @throws NullPointerException if the specified object is null
         * @throws ClassCastException   if the specified object's type prevents it
         *                              from being compared to this object.
         * @apiNote It is strongly recommended, but <i>not</i> strictly required that
         * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
         * class that implements the {@code Comparable} interface and violates
         * this condition should clearly indicate this fact.  The recommended
         * language is "Note: this class has a natural ordering that is
         * inconsistent with equals."
         */
        @Override
        public int compareTo(TotalDistance o) {
            if (o == null) throw new NullPointerException("specified object is null");
            return distance - o.distance;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            if (!(o instanceof TotalDistance d)) return false;
            return (v == d.v) && (distance == d.distance);
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
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(g, 0);
        System.out.println(Arrays.toString(dijkstraAlgorithm.edgeTo));
        System.out.println(Arrays.toString(dijkstraAlgorithm.distTo));
    }
}
