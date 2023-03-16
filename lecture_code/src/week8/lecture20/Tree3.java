package week8.lecture20;

/**
 * Store keys in an array. Don't store structure anywhere.
 * To interpret array: Simply assume tree is complete.
 * Obviously only works for "complete" trees.
 */
public class Tree3<Key extends Comparable<Key>> {
    Key[] keys;

    public void swim(int k) {
        if (keys[parent(k)].compareTo(keys[k]) > 0) {
            swap(k, parent(k));
            swim(parent(k));
        }
    }

    private int parent(int k) {
//        if (k < 3) {
//            return 0;
//        }
        return (k - 1) / 2;
    }

    private void swap(int i, int j) {
        Key temp = keys[i];
        keys[i] = keys[j];
        keys[j] = temp;
    }

}
