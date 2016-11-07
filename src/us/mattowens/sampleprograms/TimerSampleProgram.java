package us.mattowens.sampleprograms;

import java.util.Timer;
import java.util.TimerTask;

public class TimerSampleProgram {
	
	public static void main(String[] args) {
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}, 1000);
	}

}
