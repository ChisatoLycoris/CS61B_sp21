package lecture10;

public class Maximizer {
    public static Comparable max(Comparable[] items) {
        int maxDex = 0;
        for (int i = 0; i < items.length; i++) {
            int cmp = items[i].compareTo(items[maxDex]);
            if(cmp > 0) {
                maxDex = i;
            }
        }
        return  items[maxDex];
    }

//    public static void main(String[] args) {
//        Dog[] dogs = {new Dog(3, "Elyse"), new Dog(9, "Sture"),
//                        new Dog(15, "Artemesios")};
//        Dog maxDog = (Dog) max(dogs);
//        maxDog.bark();
//    }
}
