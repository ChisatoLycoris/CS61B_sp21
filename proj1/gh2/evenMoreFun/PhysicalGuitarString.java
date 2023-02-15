package gh2.evenMoreFun;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;
import gh2.GuitarString;

public class PhysicalGuitarString extends GuitarString {
    public PhysicalGuitarString(double frequency) {
        super(frequency);
    }

    public void freeze() {
        for (int i = 0; i < buffer.size(); i++) {
            buffer.removeFirst();
            buffer.addLast(0.0);
        }
    }

    public static void main(String[] args) {
        InstrumentHero hero = new InstrumentHero(Instrument.PhysicalGuitarString);
        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = InstrumentHero.KEYBOARD.indexOf(String.valueOf(key));
                if (index >= 0) {
                    hero.instruments[index].pluck();
                    for (int i = 0; i < 37; i++) {
                        if (i % 6 == index % 6) {
                            hero.instruments[i].freeze();
                        }
                    }
                }
            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for (GuitarString i : hero.instruments) {
                sample += i.sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (GuitarString i : hero.instruments) {
                i.tic();
            }
        }
    }
}
