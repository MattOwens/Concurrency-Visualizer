package us.mattowens.sampleprograms;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest implements Callable<Integer> {
	
	public Integer call() {
		System.out.println("Called");
		
		return 1;
	}
	
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		CallableTest test = new CallableTest();
		FutureTask<Integer> task = new FutureTask<Integer>(test);
		Thread t = new Thread(task);
		t.start();
		
	}

}
