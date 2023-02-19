package week6.lecture14;

import java.util.LinkedList;
import java.util.List;

public class WQNPathCompressionDS implements DisjointSets{
    private int[] parent;
    private int[] size;

    public WQNPathCompressionDS(int N) {
        parent = new int[N];
        size = new  int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = -1;
            size[i] = 1;
        }
    }

    /**
     * On calls to find(), set parent id to the root for all items seen.
     * M operations on N nodes is O(N + M lg* N)
     * lg* is called iterated logarithm
     * */
    public int find(int p) {
        int r = p;
        List<Integer> records = new LinkedList<>();
        while (parent[r] >= 0) {
            records.add(r);
            r = parent[r];
        }
        for (int i : records) {
            parent[i] = r;
        }
        return r;
    }

    @Override
    public void connect(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (size[i] > size[j]) {
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
