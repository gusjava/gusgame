package gus.game5.core.util;

import java.util.Date;

public class UtilCompare {

	/*
	 * INF 1
	 */
	
	public static boolean inf1(Object o1, Object o2) {
		if (o1 == null) return false;
		if (o2 == null) return false;

		if (o1 instanceof Number && o2 instanceof Number) 
			return numberToDouble(o1) <= numberToDouble(o2);
		if (o1 instanceof Date && o2 instanceof Date) 
			return dateToLong(o1) <= dateToLong(o2);
			
		return false;
	}
	
	/*
	 * INF 2
	 */
	
	public static boolean inf2(Object o1, Object o2) {
		if (o1 == null) return false;
		if (o2 == null) return false;

		if (o1 instanceof Number && o2 instanceof Number) 
			return numberToDouble(o1) < numberToDouble(o2);
		if (o1 instanceof Date && o2 instanceof Date) 
			return dateToLong(o1) < dateToLong(o2);
			
		return false;
	}
	
	/*
	 * SUP 1
	 */
	
	public static boolean sup1(Object o1, Object o2) {
		if (o1 == null) return false;
		if (o2 == null) return false;

		if (o1 instanceof Number && o2 instanceof Number) 
			return numberToDouble(o1) >= numberToDouble(o2);
		if (o1 instanceof Date && o2 instanceof Date) 
			return dateToLong(o1) >= dateToLong(o2);
			
		return false;
	}
	
	/*
	 * SUP 2
	 */
	
	public static boolean sup2(Object o1, Object o2) {
		if (o1 == null) return false;
		if (o2 == null) return false;

		if (o1 instanceof Number && o2 instanceof Number) 
			return numberToDouble(o1) > numberToDouble(o2);
		if (o1 instanceof Date && o2 instanceof Date) 
			return dateToLong(o1) > dateToLong(o2);
			
		return false;
	}
	
	
	

	private static double numberToDouble(Object obj) {
		return ((Number) obj).doubleValue();
	}

	private static long dateToLong(Object obj) {
		return ((Date) obj).getTime();
	}
}
