package us.mattowens.concurrencyvisualizer.sampleprograms.ForkJoinPool;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;

public aspect ForkJoinPoolDataCapture {

	pointcut create() :
		call(ForkJoinPool.new());
	
	pointcut createParallelism(int parallelism) :
		call(ForkJoinPool.new(int)) &&
		args(parallelism);
	
	//This probably won't match anything
	pointcut createTons(int parallelism, ForkJoinPool.ForkJoinWorkerThreadFactory factory,
			Thread.UncaughtExceptionHandler handler, boolean asyncMode) :
		call(ForkJoinPool.new(int, ForkJoinPool.ForkJoinWorkerThreadFacotry, 
			Thread.UncaughtExeceptionHandler, boolean)) &&
		args(parallelism, factory, handler, asyncMode);
	
	pointcut awaitTermination(ForkJoinPool pool, long timeout, TimeUnit unit) :
		call(boolean ForkJoinPool.awaitTermination(long, TimeUnit)) &&
		target(pool) &&
		args(timeout, unit);
	
	pointcut drainTasksTo(ForkJoinPool pool, Collection<? super ForkJoinTask<?>> c) :
		call(protected int ForkJoinPool.drainTasksTo(Collection<? super ForkJoinTask<?>>)) &&
		target(pool) &&
		args(c);
	
	pointcut executeTask(ForkJoinPool pool, ForkJoinTask<?> task) :
		call(void ForkJoinPool.execute(ForkJoinTask<?>)) &&
		target(pool) &&
		args(task);
	
	pointcut executeRunnable(ForkJoinPool pool, Runnable task) :
		call(void ForkJoinPool.execute(Runnable)) &&
		target(pool) &&
		args(task);
	
	pointcut invoke(ForkJoinPool pool, ForkJoinTask<?> task) :
		call(* ForkJoinPool.invoke(ForkJoinTask<?>)) &&
		target(pool) &&
		args(task);
	
	pointcut invokeAll(ForkJoinPool pool, Collection<? extends Callable<?>> tasks) :
		call(* ForkJoinPool.invokeAll(Collection<? extends Callable<T>>)) &&
		target(pool) &&
		args(tasks);
	
	pointcut managedBlock(ForkJoinPool.ManagedBlocker blocker) :
		call(void ForkJoinPool.managedBlock(ForkJoinPool.ManagedBlocker)) &&
		args(blocker);
	
	pointcut newTaskForCallable(ForkJoinPool pool, Callable<?> callable) :
		call(* ForkJoinPool.newTaskFor(Callable<?>)) &&
		target(pool) &&
		args(callable);
	
	//pointcut newTaskForRunnable(ForkJoinPool pool, Runnable runnable, T value)
	
	pointcut pollSubmission(ForkJoinPool pool) :
		call(* ForkJoinPool.pollSubmission()) &&
		target(pool);
	
	pointcut shutdown(ForkJoinPool pool) :
		call(void ForkJoinPool.shutdown()) &&
		target(pool);
	
	pointcut shutdownNow(ForkJoinPool pool) :
		call(* ForkJoinPool.shutdownNow()) &&
		args(pool);
	
	pointcut submitCallable(ForkJoinPool pool, Callable<?> task) :
		call(* ForkJoinPool.submit(Callable<?>)) &&
		target(pool) &&
		args(task);
	
	pointcut submitRunnable(ForkJoinPool pool, Runnable task) :
		call( * ForkJoinPool.submit(Runnable)) &&
		target(pool) &&
		args(task);
}
	