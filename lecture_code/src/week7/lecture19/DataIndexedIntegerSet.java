package week7.lecture19;

public class DataIndexedIntegerSet {
    private boolean[] present;

    public DataIndexedIntegerSet() {
        present = new boolean[2000000000];
    }

    public void add(int i) {
        present[i] = true;
    }

    public boolean contains(int i) {
        return present[i];
    }
}
