package us.mattowens.sampleprograms;

public class SingleThreadTest {

	public static void main(String[] args) {
		try {
			for(int i = 0; i < 10; i++) {
				Thread.sleep(100);
			}
		} catch(InterruptedException e) { }
	}
}
