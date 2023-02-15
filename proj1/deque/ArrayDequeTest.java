package deque;

import org.junit.Test;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/** Performs some basic array list tests. */
public class ArrayDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {


        ArrayDeque<String> ad1 = new ArrayDeque<String>();

        assertTrue("A newly initialized LLDeque should be empty", ad1.isEmpty());
        ad1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, ad1.size());
        assertFalse("ad1 should now contain 1 item", ad1.isEmpty());

        ad1.addLast("middle");
        assertEquals(2, ad1.size());

        ad1.addLast("back");
        assertEquals(3, ad1.size());

        System.out.println("Printing out deque: ");
        ad1.printDeque();

    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {


        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        // should be empty
        assertTrue("ad1 should be empty upon initialization", ad1.isEmpty());

        ad1.addFirst(10);
        // should not be empty
        assertFalse("ad1 should contain 1 item", ad1.isEmpty());

        ad1.removeFirst();
        // should be empty
        assertTrue("ad1 should be empty after removal", ad1.isEmpty());

    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {


        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(3);

        ad1.removeLast();
        ad1.removeFirst();
        ad1.removeLast();
        ad1.removeFirst();

        int size = ad1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);

    }

    @Test
    /* Check if you can create ArrayDeques with different parameterized types*/
    public void multipleParamTest() {


        ArrayDeque<String>  ad1 = new ArrayDeque<String>();
        ArrayDeque<Double>  ad2 = new ArrayDeque<Double>();
        ArrayDeque<Boolean> ad3 = new ArrayDeque<Boolean>();

        ad1.addFirst("string");
        ad2.addFirst(3.14159);
        ad3.addFirst(true);

        String s = ad1.removeFirst();
        double d = ad2.removeFirst();
        boolean b = ad3.removeFirst();

    }

    @Test
    /* check if null is return when removing from an empty ArrayDeque. */
    public void emptyNullReturnTest() {


        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, ad1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, ad1.removeLast());


    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigArrayDequeTest() {


        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            ad1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) ad1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) ad1.removeLast(), 0.0);
        }


    }

    @Test
    public void printDequeTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            ad1.addLast(i);
        }
        ad1.printDeque();
    }

    @Test
    /* check get items from LinkedListDeque */
    public void getTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 0; i < 100; i++) {
            ad1.addLast(i);
            assertEquals(Integer.valueOf(i), ad1.get(i));
        }

        ArrayDeque<String> ad2 = new ArrayDeque<>();
        for (int i = 0; i < 100; i++) {
            String temp = Integer.valueOf(i).toString();
            ad2.addLast(temp);
            assertEquals(temp , ad2.get(i));
        }
    }

    @Test
    /* check iterator */
    public void iteratorTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 0; i < 100; i++) {
            ad1.addLast(i);
        }
        Iterator<Integer> ad1Iterator = ad1.iterator();
        int i = 0;
        while (ad1Iterator.hasNext()) {
            assertEquals(Integer.valueOf(i), ad1Iterator.next());
            i += 1;
        }
        i = 0;
        for (Integer j : ad1) {
            assertEquals(Integer.valueOf(i), j);
            i += 1;
        }
    }

    @Test
    /* check equals method */
    public void equalsTest() {
        Deque<Integer> ad1 = new ArrayDeque<>();
        Deque<Integer> ad2 = new ArrayDeque<>();
        for (int i = 0; i < 100; i++) {
            ad1.addFirst(i);
            ad2.addFirst(i);
        }
        assertTrue(ad1.equals(ad2));
        ArrayDeque<Integer> ad3 = (ArrayDeque<Integer>)ad2;
        assertTrue(ad1.equals(ad3));
        ad2.removeLast();
        assertFalse(ad1.equals(ad2));
        assertFalse(ad1.equals(ad3));
        assertTrue(ad2.equals(ad3));
    }

    @Test
    public void randomizedAddRemoveTest() {
        Deque<Integer> ad1 = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder("\n");
        for (int i = 0; i < 1000; i++) {
            int action = ThreadLocalRandom.current().nextInt(0, 4);
            switch (action) {
                case 0:
                    ad1.addFirst(i);
                    sb.append("adFirst(").append(i).append(")\n");
                    assertEquals(sb.toString(), Integer.valueOf(i), ad1.get(0));
                    break;
                case 1:
                    ad1.addLast(i);
                    sb.append("adLast(").append(i).append(")\n");
                    assertEquals(sb.toString(), Integer.valueOf(i), ad1.get(ad1.size() - 1));
                    break;
                case 2:
                    if (ad1.isEmpty()) {
                        break;
                    }
                    sb.append("removeFirst()");
                    assertEquals(sb.toString(), ad1.get(0), ad1.removeFirst());
                case 3:
                    if (ad1.isEmpty()) {
                        break;
                    }
                    sb.append("removeLast()");
                    assertEquals(sb.toString(), ad1.get(ad1.size() - 1), ad1.removeLast());
            }
        }
    }

    @Test
    public void extremelyAddRemoveTest() {
        Deque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            ad1.addFirst(i);
            ad1.removeLast();
        }
    }
}
