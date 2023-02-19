package week1.lecture2;

public class Dog {
    public int weightInPounds;
    public static String binomen = "Canis familiaris";

    /** One Integer constructor for dogs. */
    public Dog(int w) {
        weightInPounds = w;
    }
    public void makeNoise() {
        if (weightInPounds < 10) {
            System.out.print("yip!");
        } else if (weightInPounds < 30) {
            System.out.print("Bark!");
        } else {
            System.out.print("wooooof!");
        }
    }

    public static Dog maxDog(Dog d1, Dog d2) {
        if (d1.weightInPounds > d2.weightInPounds) {
            return d1;
        }
        return d2;
    }

    public Dog maxDog(Dog d2) {
        if (this.weightInPounds > d2.weightInPounds) {
            return this;
        }
        return d2;
    }

//    public static void main(String[] args) {
//        makeNoise();
//    }
}
