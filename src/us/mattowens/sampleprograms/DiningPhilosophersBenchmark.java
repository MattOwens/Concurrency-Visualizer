package us.mattowens.sampleprograms;

public class DiningPhilosophersBenchmark {

	public static void main(String[] args) {
		long[] runDurations = new long[15];
		
		for(int i = 0; i < 15; i++) {
			long startTime = System.nanoTime();
			DiningPhilosophers.main(args);
			long endTime = System.nanoTime();
			runDurations[i] = endTime - startTime;
		}
		
		for(long time : runDurations) {
			System.out.print(time + ",");
		}
	}
}
