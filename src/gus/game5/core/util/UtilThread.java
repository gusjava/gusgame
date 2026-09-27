package gus.game5.core.util;

public class UtilThread {

	public static void run(Runnable r) {
		Thread t = new Thread(r);
		t.start();
	}
	
	public static void sleep(long duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {}
	}
	
	public static void runAfter(long duration, Runnable r) {
		run(()-> {
			sleep(duration);
			r.run();
		});
	}
}
