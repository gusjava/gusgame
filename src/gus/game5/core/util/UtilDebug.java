package gus.game5.core.util;

public class UtilDebug {

	private static int count = 0;
	
	public static void count() {
		count++;
		if(count%100==0) System.out.println(count);
	}
}
