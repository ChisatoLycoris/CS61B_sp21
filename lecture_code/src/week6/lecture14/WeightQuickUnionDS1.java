package week6.lecture14;

/* Use values other than -1 in parent array for root nodes to track size. */
public class WeightQuickUnionDS1 implements DisjointSets{
    private int[] parent;

    public WeightQuickUnionDS1(int N) {
        parent = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = -1;
        }
    }

    /**
     * @param p
     * @return root index in result[0], size of set in result[1]
     */
    public int[] find(int p) {
        int[] result = new int[2];
        int r = p;
        while (parent[r] >= 0) {
            r = parent[r];
        }
        result[0] = r;
        result[1] = - parent[r];
        return result;
    }

    @Override
    public void connect(int p, int q) {
        int[] i = find(p);
        int[] j = find(q);
        if (i[1] > i[2]) {
            parent[j[0]] = i[0];
            parent[i[0]] = - i[1] - j[1];
        } else {
            parent[i[0]] = j[0];
            parent[j[0]] = - i[1] - j[1];
        }
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p)[0] == find(q)[0];
    }
}
