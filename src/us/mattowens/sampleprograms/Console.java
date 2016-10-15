package us.mattowens.sampleprograms;

/**
 * Console Object represents a single system of a Valve,
 * Heater, PressureSensor, and TemperatureSensor.
 *
 * Methods are synchronized to allow the console to wait
 * until both the Heater and the Valve have reported with
 * their readings and corrections.
 *
 * This class implements Runnable so that it can be
 * run as its own Thread in a piece of a larger system if
 * necessary.
 */
public class Console implements Runnable {
    private int temp, pres, open;
    private boolean heaterFlag, valveFlag;
    private String heaterState;

    /**
     * Start the threads to control the Heater and the Valve
     * and continually output reported data.
     */
    public void run() {
        Thread heater = new Thread(new Heater(this));
        Thread valve = new Thread(new Valve(this));
        heater.start();
        valve.start();
        System.out.println("System started");

        while(true) {
            output();
        }

    }

    /**
     * The Heater calls this method to report its data.
     * @param temp The temperature read by the TemperatureSensor
     * @param isOn The state of the Heater
     */
    public synchronized void heaterReport(int temp, boolean isOn) {
        this.temp = temp;
        if(isOn == true) heaterState = "on";
        else heaterState = "false";
        heaterFlag = true;
        this.notify();
    }

    /**
     * The Valve calls this method to report its data.
     * @param pres The pressure read by the PressureSensor
     * @param open The angle in degrees of the Valve
     */
    public synchronized void valveReport(int pres, int open) {
        this.pres = pres;
        this.open = open;
        valveFlag = true;
        this.notify();
    }

    /**
     * The Console calls this method to output the
     * data reported by the Heater and Valve
     */
    public synchronized void output() {
        //Wait until both values have been updated
        while(!heaterFlag || !valveFlag) {
           try{
               this.wait();
           }
           catch (InterruptedException e) {}
        }
        //Output new values
        System.out.println("Temperature: " + temp + " -- Heater: " + heaterState);
        System.out.println("Pressure: " + pres + " -- Opening: " + open);
        System.out.println();

        //Reset flags
        heaterFlag = false;
        valveFlag = false;
    }
}
