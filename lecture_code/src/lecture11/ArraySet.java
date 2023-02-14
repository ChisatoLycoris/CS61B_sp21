package lecture11;

import java.util.*;

public class ArraySet<T> implements Iterable<T>{
    private T[] items;
    private int size; // the next item to be added will be at position size

    public ArraySet() {
        items = (T[])new Object[100];
        size = 0;
    }

    /* Returns true if this map contains a mapping for the specified key.
     */
    public boolean contains(T x) {
        for (int i = 0; i < size; i++) {
            if (x.equals(items[i])) {
                return true;
            }
        }
        return false;
    }

    /* Associates the specified value with the specified key in this map.
       Throws an IllegalArgumentException if the key is null. */
    public void add(T x) {
        if (x == null) {
            throw new IllegalArgumentException("You can't add null to ArraySet.");
        }
        if (contains(x)) {
            return;
        }
        items[size] = x;
        size += 1;
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    }

    /** returns an iterator (a.k.a. seer) into ME */
    public Iterator<T> iterator() {
        return  new ArraySetIterator();
    }

    private class ArraySetIterator implements Iterator<T> {
        private int wizPos;

        public ArraySetIterator() {
            wizPos = 0;
        }
        @Override
        public boolean hasNext() {
            return wizPos < size;
        }

        @Override
        public T next() {
            T returnItem = items[wizPos];
            wizPos += 1;
            return returnItem;
        }
    }

    /*
    @Override
    public String toString(){
        StringBuilder returnSB = new StringBuilder("}");
        for (int i = 0; i < size - 1; i++) {
            returnSB.append(items[i].toString());
            returnSB.append(", ");
        }
        returnSB.append(items[size - 1].toString());
        returnSB.append("}");
        return returnSB.toString();
    }
    */

    @Override
    public String toString() {
        List<String> listOfItems = new ArrayList<>();
        for (T x : this) {
            listOfItems.add(x.toString());
        }
        return "{" + String.join(", ", listOfItems) + "}";
    }

    /*
    String is immutable, so adding even a single character to a String
    creates an entirely new String.
    Which cause concatenating String very slow.
    @Override
    public String toString(){
        String returnString = "}";
        for (int i = 0; i < size - 1; i++) {
            returnString += items[i].toString();
            returnString += ", ";
        }
        returnString += items[size - 1].toString();
        returnString += "}";
        return returnString;
    }
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (! (o instanceof ArraySet arraySet)) {return false;}
        if (this.size != arraySet.size) {return false;}
        for (T x: this) {
            if (!arraySet.contains(x)) {return false;}
        }
        return true;
    }

    public static <Glerp> ArraySet<Glerp> of(Glerp... stuff) {
        ArraySet<Glerp> returnSet = new ArraySet<>();
        for (Glerp x : stuff) {
            returnSet.add(x);
        }
        return returnSet;
    }

    public static void main(String[] args) {
//        ArraySet<String> s = new ArraySet<>();
//        s.add(null);
//        s.add("horse");
//        s.add("fish");
//        s.add("house");
//        s.add("fish");
//        System.out.println(s.contains("horse"));
//        System.out.println(s.size());
//
//        Set<String> s2 = new HashSet<>();
//        s2.add(null);
//        System.out.println(s2.contains(null));

        Set<Integer> javaset = new HashSet<>();
        javaset.add(5);
        javaset.add(23);
        javaset.add(42);

        Iterator<Integer> seer = javaset.iterator();
        while (seer.hasNext()) {
            int i = seer.next();
            System.out.println(i);
        }

        for (int i : javaset) {
            System.out.println(i);
        }

        ArraySet<Integer> aset = new ArraySet<>();
        aset.add(5);
        aset.add(23);
        aset.add(42);

        Iterator<Integer> aseer = aset.iterator();
        while (aseer.hasNext()) {
            int i = aseer.next();
            System.out.println(i);
        }

        for (int i : aset) {
            System.out.println(i);
        }

        ArraySet<String> asetOfStrings = ArraySet.of("hi", "I'm", "here");
        System.out.println(asetOfStrings);
    }

    /* Also to do:
    1. Make ArraySet implement the Iterable<T> interface.
    2. Implement a toString method.
    3. Implement an equals() method.
    */
}
