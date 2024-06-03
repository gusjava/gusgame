package gus.game5.core.util;

import gus.game5.core.exception.TechnicalException;

public class UtilBoolean {
	
	/*
	 * CHECKS
	 */
	
	public static Boolean check(Object obj) {
		if(obj==null) return null;
		if(obj instanceof Boolean) return (Boolean) obj;
		
		throw new TechnicalException("Invalid data type: "+obj.getClass().getName());
	}
	
	public static Boolean check2(Object obj) {
		if(obj==null) return null;
		if(obj instanceof Boolean) return (Boolean) obj;
		if(obj instanceof Integer) return numberToBoolean((Integer) obj);
		
		throw new TechnicalException("Invalid data type: "+obj.getClass().getName());
	}
	
	
	/*
	 * TO BOOLEAN
	 */
	
	public static Boolean objToBoolean(Object obj) {
		if(obj==null) return null;
		
		if(obj instanceof Boolean) return (Boolean) obj;
		if(obj instanceof Number) return numberToBoolean((Number) obj);
		if(obj instanceof String) return stringToBoolean((String) obj);
		
		throw new TechnicalException("Failed to convert data into Boolean (Unsupported type: "+obj.getClass().getName()+")");
	}
	
	public static Boolean numberToBoolean(Number n) {
		if(n==null) return null;
		return ((Number) n).intValue()==1;
	}
	
	public static Boolean stringToBoolean(String s) {
		if(s==null) return null;
		
		s = s.trim();
		if(s.equals("")) return null;
		if(s.equals("null")) return null;
		
		s = s.toLowerCase();
		if(s.equals("oui") || s.equals("vrai") || s.equals("yes") || s.equals("true")) return true;
		if(s.equals("non") || s.equals("faux") || s.equals("no") || s.equals("false")) return false;
		
		throw new TechnicalException("Failed to convert data into boolean (Unsupported string: "+s+")");
	}
	
	

	
	public static boolean objToBoolDF(Object obj) {
		return objToBoolD(obj,false);
	}
	
	public static boolean objToBoolDT(Object obj) {
		return objToBoolD(obj,true);
	}
	
	public static boolean objToBoolD(Object obj, boolean defaultValue) {
		Boolean b = objToBoolean(obj);
		return b!=null ? b.booleanValue() : defaultValue;
	}

	
	
	
	/*
	 * BOOLEAN TO ...
	 */
	
	public static String booleanToString(Boolean value) {
		if(value==null) return null;
		return Boolean.toString(value);
	}

	public static Integer booleanToInteger(Boolean value) {
		if(value==null) return null;
		return value ? 1 : null;
	}
	
	
	/*
	 * DISPLAY
	 */
	
	public static String display(Boolean value) {
		return display(value,"Oui","Non");
	}
	
	public static String display(Boolean value, String valueTrue, String valueFalse) {
		if(value==null) return "";
		return value ? valueTrue : valueFalse;
	}
	
	
	
	/*
	 * COUNT
	 */
	
	public static int countT(boolean... bb) {
		int count = 0;
		for(boolean b : bb) if(b) count++;
		return count;
	}
	
	public static int countF(boolean... bb) {
		int count = 0;
		for(boolean b : bb) if(!b) count++;
		return count;
	}
	
	
	/*
	 * TOGGLE
	 */
	
	public static Boolean toggle(Boolean value) {
		return toggle(value,null);
	}
	
	public static Boolean toggle(Boolean value, Boolean defaultValue) {
		if(value==null) return defaultValue;
		return value ? Boolean.FALSE : Boolean.TRUE;
	}
	
	public static Boolean toggleDT(Boolean value) {
		return toggle(value,true);
	}
	
	public static Boolean toggleDF(Boolean value) {
		return toggle(value,false);
	}
	
	/*
	 * TO BOOL
	 */
	
	public static boolean toBool(Boolean value, boolean defaultValue) {
		return value!=null ? value.booleanValue() : defaultValue;
	}
	public static boolean toBoolNot(Boolean value, boolean defaultValue) {
		return value!=null ? !value.booleanValue() : defaultValue;
	}
	
	
	/*
	 * IS
	 */
	
	public static boolean isTrue(Boolean value) {
		return value!=null && value.booleanValue();
	}
	public static boolean isFalse(Boolean value) {
		return value!=null && !value.booleanValue();
	}
	
	
	/*
	 * PRINT
	 */
	
	public static boolean print(boolean b) {
		System.out.println("BOOLEAN: "+b);
		return b;
	}
}
