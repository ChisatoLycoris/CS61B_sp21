package week6.lecture14;

/* Create a separate array to keep track of size. */
public class WeightQuickUnionDS2 implements DisjointSets{
    private int[] parent;
    private int[] size;

    public WeightQuickUnionDS2(int N) {
        parent = new int[N];
        size = new  int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = -1;
            size[i] = 1;
        }
    }

    public int find(int p) {
        int r = p;
        while (parent[r] >= 0) {
            r = parent[r];
        }
        return r;
    }

    @Override
    public void connect(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i > j) {
            parent[j] = i;
            size[i] = size[i] + size[j];
        } else {
            parent[i] = j;
            size[j] = size[i] + size[j];
        }
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }
}
