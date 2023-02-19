package discussion5;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class IteratorOfIterators implements Iterator<Integer> {
    List<Iterator<Integer>> list;
    int index;

    public IteratorOfIterators(List<Iterator<Integer>> a) {
        list = a;
        index = 0;
    }

    @Override
    public boolean hasNext() {
        for (Iterator<Integer> integerIterator : list) {
            if (integerIterator.hasNext()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int current = index;
        moveIndex();
        if (list.get(current).hasNext()) {
            return list.get(current).next();
        }
        return next();
    }

    private void moveIndex() {
        if (index != list.size() - 1) {
            index += 1;
        } else {
            index = 0;
        }
    }

    public static void main(String[] args) {
        List<Integer> a = List.of(1, 3, 4, 5);
        List<Integer> b = new LinkedList<>();
        List<Integer> c = List.of(2);

        List<Iterator<Integer>> list = List.of(a.iterator(), b.iterator(), c.iterator());
        IteratorOfIterators ioi = new IteratorOfIterators(list);
        while (ioi.hasNext()) {
            System.out.println(ioi.next());
        }
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