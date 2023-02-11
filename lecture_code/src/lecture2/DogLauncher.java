package lecture2;

public class DogLauncher {
    public static void main(String[] args) {
        Dog d = new Dog(15);
//        d.weightInPounds = 25;
//        d.makeNoise();
        Dog d2 = new Dog(100);
        Dog bigger = d.maxDog(d2);
        bigger.makeNoise();
//        System.out.print(d.binomen);
//        System.out.print(d2.binomen);
        System.out.print(Dog.binomen);
    }
}