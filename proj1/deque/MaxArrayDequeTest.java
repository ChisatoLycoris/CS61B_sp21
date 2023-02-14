package deque;

import org.junit.Test;
import static org.junit.Assert.*;

public class MaxArrayDequeTest {

    @Test
    public void maxTest() {
        MaxArrayDeque<Integer> integerMaxArrayDeque= new MaxArrayDeque<>((Integer i1, Integer i2) ->{
            return i1.compareTo(i2);
        });

        integerMaxArrayDeque.addFirst(3);
        integerMaxArrayDeque.addFirst(13);
        integerMaxArrayDeque.addFirst(-5);
        integerMaxArrayDeque.addFirst(7);
        integerMaxArrayDeque.addFirst(9);
        integerMaxArrayDeque.addFirst(13);
        integerMaxArrayDeque.addFirst(-17);

        assertEquals(Integer.valueOf(13), integerMaxArrayDeque.max());
        assertEquals(Integer.valueOf(-17), integerMaxArrayDeque.max((Integer i1, Integer i2) -> {
            return Integer.valueOf(i1 * i1).compareTo(i2 * i2);
        }));
    }
}
