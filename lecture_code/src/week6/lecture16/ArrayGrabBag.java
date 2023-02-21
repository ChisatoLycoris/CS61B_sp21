package week6.lecture16;

public class ArrayGrabBag implements GrabBag{
    private int[] items;
    private int size;
    private double usage;

    public ArrayGrabBag() {
        items = new int[8];
        size = 0;
        usage = 0;
    }

    private void resize(double newSize) {
        int[] newArr = new int[(int) (size * newSize)];
        System.arraycopy(items, 0, newArr, 0, size);
        items = newArr;
    }

    @Override
    public void insert(int x) {
        if (usage > 0.75) {
            resize(1.2);
        }
        items[size] = x;
        size += 1;
        usage = size / (double) items.length;
    }

    @Override
    public int remove() {
        int randomIndex = (int) (Math.random() * size);
        int randomvalue = items[randomIndex];
        items[randomIndex] = items[size - 1];
        size -= 1;
        if (usage < 0.3) {
            resize(0.5);
        }
        return randomvalue;
    }

    @Override
    public int sample() {
        int randomIndex = (int) (Math.random() * size);
        return items[randomIndex];
    }

    @Override
    public int size() {
        return size;
    }
}
