package week6.lecture14;

public interface DisjointSets {
    /** Connects two items P and Q. */
    void connect(int p, int q);
    /**
     * Checks to see if two items are connected.
     * Connections can be transitive, i.e. they don't need to be direct.
     * */
    boolean isConnected(int p, int q);
}
