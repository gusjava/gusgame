package gus.game5.core.util;


public class UtilDurationMS {
	
	public static final long SEC = 1000;		//ms
	public static final long MIN = 60_000;		//ms
	public static final long HOUR = 3600_000;	//ms
	public static final long DAY = 86400_000;	//ms
	
	public static final String U_d = "J";
	public static final String U_h = "H";
	public static final String U_m = "min";
	public static final String U_s = "s";
	public static final String U_ms = "ms";
	
	
	/*
	 * DISPLAY (MS)
	 */
	
	public static String getDisplay(long duration) {
		if(duration<0) return ""+duration;
		if(duration==0) return "0"+U_ms;
		
		StringBuffer b = new StringBuffer();
		
		int days = (int) (duration/DAY);
		if(days>0) b.append(days+U_d+" ");
		duration = duration%DAY;
		
		int hours = (int) (duration/HOUR);
		if(hours>0) b.append(hours+U_h+" ");
		duration = duration%HOUR;
		
		int min = (int) (duration/MIN);
		if(min>0) b.append(min+U_m+" ");
		duration = duration%MIN;
		
		int sec = (int) (duration/SEC);
		if(sec>0) b.append(sec+U_s+" ");
		duration = duration%SEC;
		
		int ms = (int) duration;
		if(ms>0) b.append(ms+U_ms+" ");
		
		b.deleteCharAt(b.length()-1);
		return b.toString();
	}
	
	
	/*
	 * DURATION (MS)
	 */
	
	public static long now() {
		return System.currentTimeMillis();
	}
	
	public static long toNow(long t) {
		return System.currentTimeMillis()-t;
	}
	
	/*
	 * DURATION (NS)
	 */
	
	public static long nowNS() {
		return System.nanoTime();
	}
	
	public static long toNowNS(long t) {
		return System.nanoTime()-t;
	}
	
	/*
	 * DURATION LEFT
	 */
	
	public static String getDisplayLeft(long startTime, int current, int total) {
		if(current==0) return "?";
		if(current>=total) return "0";
		long dt = System.currentTimeMillis() - startTime;
		double factor = (double) (total-current) / (double) current;
		return getDisplay((long) (dt * factor));
	}
}
