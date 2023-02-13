package lecture10;

import java.util.Comparator;

public class Dog implements Comparable<Dog>{
    private String name;
    private int size;

    public Dog(int s, String n) {
        name = n;
        size = s;
    }

    public void bark() {
        System.out.println(name + "says: bark");
    }

    /** Returns negative number if this dog is less than the dog point ed by o, and so forth. */
    @Override
    public int compareTo(Dog uddaDog) {
//        Dog uddaDog = (Dog) o;
        return this.size - uddaDog.size;
//        if (this.size < uddaDog.size) {
//            return -1;
//        } else if (this.size == uddaDog.size) {
//            return  0;
//        }
//        return 1;
    }

    private static class NameComparator implements Comparator<Dog> {

        @Override
        public int compare(Dog o1, Dog o2) {
            return o1.name.compareTo(o2.name);
        }
    }

    public static Comparator<Dog> getNameComparator() {
        return new NameComparator();
    }
}
