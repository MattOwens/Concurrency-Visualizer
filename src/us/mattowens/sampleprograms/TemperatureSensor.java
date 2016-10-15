package us.mattowens.sampleprograms;

/**

 * Created by matthew on 3/30/15.
 */

import java.util.Random;

public class TemperatureSensor {
    private Random rng;

    public TemperatureSensor() {
        this.rng = new Random();
    }

    //Returns a random integer between 75 and 85 inclusive
    public int readTemperature() {
        return rng.nextInt(11) + 75;
    }
}
