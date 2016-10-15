package us.mattowens.sampleprograms;

public class Heater implements Runnable {
    private boolean isOn;
    private Console controller;

    public Heater(Console c) {
        controller = c;
    }

    public void run() {
        int temp;
        TemperatureSensor ts = new TemperatureSensor();

        //Continually read from the TemperatureSensor
        while(true) {
            temp = ts.readTemperature();

            //Determine and execute correction
            if(temp < 80) turnOn();
            else turnOff();

            //Report reading to the Console
            controller.heaterReport(temp, isOn);

            //Wait until next reading available
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException e) {}
        }
    }

    private void turnOn() {
        isOn = true;
    }

    private void turnOff() {
        isOn = false;
    }
}
