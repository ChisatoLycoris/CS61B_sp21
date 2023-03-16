package week8.lecture20;

/**
 * Store keys in an array. Store parentIDs in an array.
 */
public class Tree2<Key> {
    Key[] keys;
    int[] parents;
}

/**
 *       k
 *     /  \
 *   e     v
 *  / \   / \
 * b   g p   y
 *
 *       0
 *     /  \
 *   1     2
 *  / \   / \
 * 3   4 5   6
 * Key[] keys : [k, e, v, b, g, p , y]
 * int[] parents : [0, 0, 0, 1, 1, 2, 2]
 */