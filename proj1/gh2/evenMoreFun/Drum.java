package gh2.evenMoreFun;

import gh2.GuitarString;

public class Drum extends GuitarString {
    public Drum(double frequency) {
        super(frequency);
    }

    @Override
    public void tic() {
        //       Dequeue the front sample and enqueue a new sample that is
        //       the average of the two multiplied by the DECAY factor.
        //       **Do not call StdAudio.play().**
        double firstSample = buffer.removeFirst();
        double secondSample = buffer.get(0);
        int sign = Math.random() < 0.5 ? -1 : 1;
        double newSample = (firstSample + secondSample) * 0.5 * DECAY * sign;
        buffer.addLast(newSample);
    }
}
