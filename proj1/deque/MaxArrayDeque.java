package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        comparator = c;
    }

    public T max() {
        if (this.isEmpty()) {
            return null;
        }
        T max = this.get(0);
        for (T i : this) {
            if (comparator.compare(max, i) < 0) {
                max = i;
            }
        }
        return max;
    }

    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }
        T max = this.get(0);
        for (T i : this) {
            if (c.compare(max, i) < 0) {
                max = i;
            }
        }
        return max;
    }
}
