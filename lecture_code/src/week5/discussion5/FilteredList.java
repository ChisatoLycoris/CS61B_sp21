package week5.discussion5;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class FilteredList<T> implements Iterable<T> {
    private List<T> list;
    private Predicate<T> filter;

    public FilteredList(List<T> L, Predicate<T> filter) {
        list = L;
        this.filter = filter;
    }
    @Override
    public Iterator<T> iterator() {
        return new FilteredListIterator<T>();
    }

    private class FilteredListIterator<T> implements Iterator<T> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < list.size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T current = (T) list.get(index);
            index += 1;
            moveIndex();
            return current;
        }

        private void moveIndex() {
            while (hasNext() && !filter.test(list.get(index))) {
                index += 1;
            }
        }
    }
}
//    Filtered List
//        We want to make a FilteredList class that selects only certain elements of a List
//        during iteration. To do so, we’re going to use the Predicate interface defined below.
//        Note that it has a method, test that takes in an argument and returns True if we
//        want to keep this argument or False otherwise.
//          public interface Predicate<T> {
//              boolean test(T x);
//          }
//        For example, if L is any kind of object that implements List<String> (that is, the
//        standard java.util.List), then writing
//        FilteredList<String> FL = new FilteredList<>(L, filter);
//        gives an iterable containing all items, x, in L for which filter.test(x) is True.
//        Here, filter is of type Predicate. Fill in the FilteredList class below.