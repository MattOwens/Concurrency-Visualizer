package us.mattowens.concurrencyvisualizer;

public  class StringConstants {
	public static final String CONFIG_FILE = "concurrencyvisualizer.conf";
	public static final String OUTFILE_KEY = "OUTPUT_FILE";
	public static final String LIVE_VIEW = "LIVE_VIEW";
	public static final String MAIN_CLASS_NAME = "MAIN_CLASS";
	
	/*
	 * Every Event
	 */
	public static final String TIMESTAMP = "ts";
	public static final String THREAD_ID = "tid";
	public static final String TARGET = "tgt";
	public static final String EVENT_CLASS = "cls";
	public static final String JOIN_POINT = "jp";
	public static final String EVENT_TYPE = "et";
	
	/*
	 * Class Specific Events
	 */
	public static final String TIMEOUT = "Timeout";
	public static final String TIME_UNIT = "Time Unit";
	public static final String SUCCESS = "Success";
	public static final String CONDITION = "Condition";
	public static final String FAIR = "Fair";
	public static final String LOCKED = "Locked";
	public static final String NANOS = "Nanos";
	public static final String RUNNABLE = "Runnable";
	public static final String NEW_NAME = "New Name";
	public static final String THREAD_GROUP = "Thread Group";
	public static final String INTERRUPTED = "Interrupted";
	public static final String MILLIS = "Millis";
	public static final String DAEMON = "Daemon";
	public static final String PRIORITY = "Priority";
	public static final String PERMITS = "Permits";
	public static final String OWNER_THREAD = "Owner Thread";
	public static final String ARG = "Arg";
	public static final String EXPECT = "Expect";
	public static final String UPDATE = "Update";
	public static final String NEW_STATE = "New State";
	public static final String TRY_ACQUIRE_RESULT = "TryAcquireResult";
	public static final String TIME_REMAINING = "Time Remaining";
	public static final String DEADLINE = "Deadline";
	public static final String COUNT = "Count";
	public static final String PARTIES = "Parties";
	public static final String ARRIVAL = "Arrival";
	public static final String FORK_JOIN_POOL = "Fork Join Pool";
	public static final String WORKER_THREAD = "Worker Thread";
	public static final String BLOCKING_UNNECESSARY = "Blocking Unnecessary";
	public static final String PARENT = "Parent";
	public static final String READ_LOCK = "Read Lock";
	public static final String WRITE_LOCK = "Write Lock";
	public static final String EXECUTOR = "Executor";
	public static final String NEW_THREAD = "New Thread";
	public static final String MAX_PRIORITY = "Max Priority";
	public static final String NUM_PURGED = "Num Purged";
	public static final String TASK = "Task";
	public static final String START_TIME = "Start Time";
	public static final String DELAY = "Delay";
	public static final String PERIOD = "Period";
	public static final String COMPLETED_NORMALLY = "Completed Normally";
	public static final String BLOCKER = "Blocker";
	public static final String THREAD = "Thread";
	
	/*
	 * Control Signal Events
	 */
	public static final String THREAD_NAME = "tname";
}
