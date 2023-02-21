package week6.lecture16;

public class BinarySearchTree<T extends Comparable<T>> {
    private final T key;
    private BinarySearchTree<T> left;
    private  BinarySearchTree<T> right;

    public BinarySearchTree(T key) {
        this.key = key;
        left = null;
        right = null;
    }

    public BinarySearchTree<T> find(BinarySearchTree<T> current, T key) {
        if (current == null) {
            return null;
        }
        if (key.equals(current.key)) {
            return current;
        } else if (key.compareTo(current.key) < 0) {
            return find(current.left, key);
        } else {
            return find(current.right, key);
        }
    }

    public BinarySearchTree<T> insert(BinarySearchTree<T> current, T key) {
        if (current == null) {
            return new BinarySearchTree<T>(key);
        }
        if (key.compareTo(current.key) < 0) {
            current.left = insert(current.left, key);
        } else if (key.compareTo(current.key) > 0) {
            current.right = insert(current.right, key);
        }
        return current;
    }
    /* Don't know how to finish this without parent pointer... */
    public BinarySearchTree<T> delete(BinarySearchTree<T> current, T key) {
        /*
         * Case 1: Deletion key has no children.
         *         -> Remove the parent pointer that point to deletion key.
         * Case 2: Deletion key has one child.
         *         -> Move the parent's pointer originally point to deletion key to child.
         * Case 3: Deletion key has two children.
         *         -> Choose either predecessor or successor.
         *            Delete chosen node and stick new copy in the deletion key position.
         *              This deletion guaranteed to be either case 1 or case 2.
         *            This strategy is sometimes known as "Hibbard deletion".
         */
        return null;
    }
}
/*
 * insert 3.5
 *      4
 *    /   \
 *   2     6
 *  / \   / \
 * 1   3 5   7
 *    ( \ )
 *    ( 3.5 )
 *
 * insert(4, 3.5)
 * 4.left = insert(4.left, 3.5)
 *          insert(2, 3.5)
 *          2.right = insert(2.right, 3.5)
 *                    insert(3, 3.5)
 *                    3.right = insert(3.right, 3.5)
 *                              new BST(3.5)
 *                    return 3
 *          return 2
 * return 4
 */

/*
 * insert 3.8
 *      4
 *    /   \
 *   2     6
 *  / \   / \
 * 1   3 5   7
 *      \
 *      3.5
 *      ( \ )
 *      ( 3.8 )
 *
 * insert(4, 3.8)
 * 4.left = insert(4.left, 3.8)
 *          insert(2, 3.8)
 *          2.right = insert(2.right, 3.8)
 *                    insert(3, 3.8)
 *                    3.right = insert(3.right, 3.8)
 *                              3.5.right = insert(3.5.right, 3.8)
 *                                          return new BST(3.8)
 *                              return 3.5
 *                    return 3
 *          return 2;
 * return 4
 */
