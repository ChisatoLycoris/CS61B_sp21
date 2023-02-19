package week5.discussion5;

import java.util.Comparator;

public class DMSComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal o1, Animal o2) {
        int first = o1.speak(new Animal());
        int second = o2.speak(new Animal());
        int third = o1.speak(new Dog());
        int fourth = o2.speak(new Dog());
        if (first == second && third == fourth) {
            return 0;
        } else if (third - fourth == 3 || first - second == 1) {
//                (third == 4 || (second == 2 && first == 3))
            return 1;
        } else {
            return -1;
        }
    }
}

class Animal {
    int speak(Dog a) {return 1; }
    int speak(Animal a) {return 2; }
}

class Dog extends Animal {
    @Override
    int speak(Animal a) {return 3; }
}

class Poodle extends Dog {
    @Override
    int speak(Dog a) {return 4; }
}

//    DMS Comparator
//    Implement the Comparator DMSComparator, which compares Animal instances. An
//        Animal instance is greater than another Animal instance if its dynamic type is
//        more specific. See the examples to the right below.
//        In the second and third blanks in the compare method, you may only use the
//        integer variables predefined (first, second, etc), relational/equality operators (==, >, etc),
//        boolean operators (&& and ||), integers, and parentheses.
//        As a challenge, use equality operators (== or !=) and no relational operators (>, <=,
//        etc). There may be more than one solution.