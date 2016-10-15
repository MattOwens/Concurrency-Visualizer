package us.mattowens.sampleprograms;

/**

 * Created by matthew on 3/30/15.
 */
import java.util.Random;
public class PressureSensor {
    private Random rng;

    public PressureSensor() {
        this.rng = new Random();
    }

    //Returns a random integer between 795 and 805 inclusive
    public int readPressure() {
        return rng.nextInt(11) + 795;
    }
}
