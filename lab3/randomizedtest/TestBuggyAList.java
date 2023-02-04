package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    /**
     * helper method for comparing AListNoResizing & BuggyAList
     * */
    public boolean simpleCompareAList(AListNoResizing aListNoResizing, BuggyAList buggyAList) {
        if (aListNoResizing.size() != buggyAList.size()) {
            return false;
        }
        for (int i = 0; i < aListNoResizing.size(); i++) {
            if (!aListNoResizing.get(i).equals(buggyAList.get(i))) {
                return false;
            }
        }
        return true;
    }
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> aListNoResizing = new AListNoResizing<>();
        aListNoResizing.addLast(4);
        aListNoResizing.addLast(5);
        aListNoResizing.addLast(6);
        BuggyAList<Integer> buggyAList = new BuggyAList<>();
        buggyAList.addLast(4);
        buggyAList.addLast(5);
        buggyAList.addLast(6);
        assertTrue(simpleCompareAList(aListNoResizing, buggyAList));

        aListNoResizing.removeLast();
        buggyAList.removeLast();
        assertTrue(simpleCompareAList(aListNoResizing, buggyAList));

        aListNoResizing.removeLast();
        buggyAList.removeLast();
        assertTrue(simpleCompareAList(aListNoResizing, buggyAList));

        aListNoResizing.removeLast();
        buggyAList.removeLast();
        assertTrue(simpleCompareAList(aListNoResizing, buggyAList));
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> R = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                R.addLast(randVal);
                // System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int size_R = R.size();
                assertEquals(size, size_R);
                // System.out.println("size: " + size);
            } else if (operationNumber == 2 && L.size() > 0) {
                // getLast
                int lastVal = L.getLast();
                int lastVal_R = R.getLast();
                assertEquals(lastVal, lastVal_R);
                // System.out.println("getLast: " + lastVal);
            } else if (operationNumber == 3 && L.size() > 0) {
                // removeLast
                int lastVal = L.removeLast();
                int lastVal_R = R.removeLast();
                assertEquals(lastVal, lastVal_R);
                // System.out.println("removeLast: " + lastVal);
            }
        }
    }
}
