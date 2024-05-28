package gus.game5.core.util;

public class UtilThread {

	public static void run(Runnable r) {
		Thread t = new Thread(r);
		t.start();
	}
}
