package gus.game5.core.util;


public class UtilDurationS {
	
	public static final long MIN = 60;		//s
	public static final long HOUR = 3600;	//s
	public static final long DAY = 86400;	//s
	public static final long YEAR = 31_536_000;	//s

	public static final String U_a = "A";
	public static final String U_d = "J";
	public static final String U_h = "H";
	public static final String U_m = "min";
	public static final String U_s = "s";
	
	
	/*
	 * DISPLAY (MS)
	 */
	
	public static String getDisplay(long duration) {
		if(duration<0) return ""+duration;
		if(duration==0) return "0"+U_s;
		
		StringBuffer b = new StringBuffer();
		
		int years = (int) (duration/YEAR);
		if(years>0) b.append(years+U_a+" ");
		duration = duration%YEAR;
		
		int days = (int) (duration/DAY);
		if(days>0) b.append(days+U_d+" ");
		duration = duration%DAY;
		
		int hours = (int) (duration/HOUR);
		if(hours>0) b.append(hours+U_h+" ");
		duration = duration%HOUR;
		
		int min = (int) (duration/MIN);
		if(min>0) b.append(min+U_m+" ");
		duration = duration%MIN;
		
		int sec = (int) duration;
		if(sec>0) b.append(sec+U_s+" ");
		
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
