package week5.discussion5;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class IteratorOfIteratorsSolution implements Iterator<Integer> {
    LinkedList<Iterator<Integer>> iterators;

    public IteratorOfIteratorsSolution(List<Iterator<Integer>> a) {
        iterators = new LinkedList<>();
        for (Iterator<Integer> iterator: a) {
            if (iterator.hasNext()) {
                iterators.add(iterator);
            }
        }
    }

    @Override
    public boolean hasNext() {
        return !iterators.isEmpty();
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Iterator<Integer> iterator = iterators.removeFirst();
        Integer next = iterator.next();
        if (iterator.hasNext()) {
            iterators.addLast(iterator);
        }
        return next;
    }
}
//    Iterator of Iterators
//        Implement an IteratorOfIterators which will accept as an argument a List of
//        Iterator objects containing Integers. The first call to next() should return the
//        first item from the first iterator in the list. The second call to next() should return
//        the first item from the second iterator in the list. If the list contained n iterators,
//        the n+1th time that we call next(), we would return the second item of the first
//        iterator in the list.
//        Note that if an iterator is empty in this process, we continue to the next iterator.
//        Then, once all the iterators are empty, hasNext should return false. For example,
//        if we had 3 Iterators A, B, and C such that A contained the values [1, 3,
//        4, 5], B was empty, and C contained the values [2], calls to next() for our
//        IteratorOfIterators would return [1, 2, 3, 4, 5].