package gh2.evenMoreFun;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;
import gh2.GuitarString;

public class InstrumentHero {
    static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
//    private I[] instrument;
//    public InstrumentHero(I[] instrument) {
//        this.instrument = instrument;
//    }

    GuitarString[] instruments = new GuitarString[37];
    public InstrumentHero(Instrument name) {
        for (int i = 0; i < 37; i++) {
            double frequency = 440 * Math.pow(2, (i - 24) / 12.0);
            switch (name) {
                case HarpString:
                    instruments[i] = new HarpString(frequency);
                    break;
                case Drum:
                    instruments[i] = new Drum(frequency);
                    break;
                case PhysicalGuitarString:
                    instruments[i] = new PhysicalGuitarString(frequency);
                default:
                    instruments[i] = new GuitarString(frequency);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        InstrumentHero hero = new InstrumentHero(Instrument.Drum);
        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = KEYBOARD.indexOf(String.valueOf(key));
                if (index >= 0) {
                    hero.instruments[index].pluck();
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
