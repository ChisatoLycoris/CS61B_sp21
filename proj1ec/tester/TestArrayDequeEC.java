package tester;

import static org.junit.Assert.*;

import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import student.Deque;
import student.StudentArrayDeque;

public class TestArrayDequeEC {

    @Test
    public void randomizedTest() {
        StudentArrayDeque<Integer> student = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<>();
        StringBuilder message = new StringBuilder("\n");
        for (int i = 0; i < 100; i += 1) {
            int action = StdRandom.uniform(4);

            switch (action) {
                case 0:
                    student.addFirst(i);
                    solution.addFirst(i);
                    assertEquals(message.append("addFirst(").append(i).append(")\n").append("get(0)\n").toString(),
                            solution.get(0), student.get(0));
                    break;
                case 1:
                    student.addLast(i);
                    solution.addLast(i);
                    assertEquals(message.append("addLast(").append(i).append(")\n")
                            .append("size()\n").append("get(").append(student.size()-1).append(")\n").toString(),
                            solution.get(solution.size() - 1), student.get(student.size() - 1));
                    break;
                case 2:
                    if (student.size() == 0) {break;}
                    assertEquals(message.append("removeFirst()\n").toString(), solution.removeFirst(), student.removeFirst());
                    break;
                case 3:
                    if (student.size() == 0) {break;}
                    assertEquals(message.append("removeLast()\n").toString(), solution.removeLast(), student.removeLast());
                    break;
            }


        }
    }

}
