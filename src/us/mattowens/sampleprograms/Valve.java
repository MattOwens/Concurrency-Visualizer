package us.mattowens.sampleprograms;

public class Valve implements Runnable {
    private int open, reading;
    private Console controller;

    public Valve(Console c) {
        controller = c;
    }

    public void run() {
        int kp = 8;
        int error;
        PressureSensor ps = new PressureSensor();

        while(true) {
            //Continually read from sensor
            reading = ps.readPressure();

            //Determine and execute correction
            error = 800-reading;
            setOpening(45+kp*error);

            //Report reading to Console
            controller.valveReport(reading, open);
            try {
                Thread.sleep(18);
            } catch (InterruptedException e) { }
        }
    }

    private void setOpening(int openingInDegrees) {
        open = openingInDegrees;
    }
}
