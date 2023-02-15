package gh2.evenMoreFun;

import gh2.GuitarString;

public class HarpString extends GuitarString {

    protected static final double DECAY = .994;
    public HarpString(double frequency) {
        super(frequency);
//        buffer.addLast(0.0);
//        buffer.addLast(0.0);
        buffer.removeLast();
        buffer.removeLast();
    }

    @Override
    public void tic() {
        double firstSample = buffer.removeFirst();
        double secondSample = buffer.get(0);
        double newSample = (firstSample + secondSample) * 0.5 * DECAY * (-1);
        buffer.addLast(newSample);
    }
}
