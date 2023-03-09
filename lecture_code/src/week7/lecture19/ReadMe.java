package week7.lecture19;

public class ReadMe {
    public static void main(String[] args) {
        System.out.println(-1 % 4);

        // Using negative hash code
        System.out.println(Math.floorMod(-1, 4));
    }
}

/*
 * data
 * |
 * v
 * hash function
 * hashCode()
 * |
 * v
 * hash code
 * |
 * v
 * reduce
 * Math.floorMod()
 * |
 * v
 * index
 *
 * Implementation see lab8
 */
