package week4.lecture10;

import java.util.Comparator;

public class DogLauncher {
    public static void main(String[] args) {
        Dog d1 = new Dog(3, "Elyse");
        Dog d2 = new Dog(9, "Sture");
        Dog d3 = new Dog(15, "Artemesios");
        Dog[] dogs = new Dog[]{d1, d2, d3};
        System.out.println((Dog) Maximizer.max(dogs));
        Dog d = (Dog) Maximizer.max(dogs);
        d.bark();

        Comparator<Dog> nc = Dog.getNameComparator();
        if(nc.compare(d1, d3) > 0) {
            d1.bark();
        } else {
            d3.bark();
        }
    }
}
