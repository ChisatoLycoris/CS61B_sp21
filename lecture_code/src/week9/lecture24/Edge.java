package week9.lecture24;

public class Edge implements Comparable<Edge>{
    private final int v;
    private final int w;
    private final int distance;

    public Edge(int v, int w, int distance) {
        this.v = v;
        this.w = w;
        this.distance = distance;
    }

    public int other(int v) {
        if (v == this.v) { return this.w; }
        else if (v == this.w) { return this.v; }
        else throw new IllegalArgumentException("No such vertices in this edge.");
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public int weight() {return distance;}

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Edge e)) { return false; }
        if (distance != e.distance) { return false; }
        return (v == e.v && w == e.w) || (v == e.w && w == e.v);
    }

    @Override
    public String toString() {
        return v + " - " + w + " (" + distance + ")";
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
    public int compareTo(Edge o) {
        return distance - o.distance;
    }
}
