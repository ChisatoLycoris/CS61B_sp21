package week6.lecture14;

public class QuickFindDS implements DisjointSets{
    private int[] id;

    public QuickFindDS(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    /* Relatively slow: N+2 to 2N+2 array accesses: O(N)*/
    @Override
    public void connect(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++) {
            if(id[i] == pid) {
                id[i] = qid;
            }
        }
    }

    /* Very fast: Two array accesses: O(1)*/
    @Override
    public boolean isConnected(int p, int q) {
        return id[p] == id[q];
    }
}
