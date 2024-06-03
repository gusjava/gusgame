package gus.game5.core.util;

import java.io.File;
import java.util.Collection;
import java.util.Map;

public class UtilEmpty {
	
	/*
	 * FILLED
	 */
	
	public static boolean isFilled(Object obj) {
		return !isEmpty(obj);
	}
	
	public static boolean isAllFilled(Object... objs) {
		if(objs==null) return false;
		for(Object obj : objs) if(isEmpty(obj)) return false;
		return true;
	}
	
	/*
	 * EMPTY
	 */
	
	public static boolean isEmpty(Object obj) {
		if(obj==null) return true;
		if(obj instanceof String) return isEmptyString((String) obj);
		if(obj instanceof Collection) return isEmptyCol((Collection<?>) obj);
		if(obj instanceof Map) return isEmptyMap((Map<?,?>) obj);
		if(obj instanceof Object[]) return isEmptyArray((Object[]) obj);
		if(obj instanceof File) return isEmptyFile((File) obj);
		return false;
	}
	
	public static boolean isAllEmpty(Object... objs) {
		if(objs==null) return true;
		for(Object obj : objs) if(isFilled(obj)) return false;
		return true;
	}
	
	/*
	 * EMPTY STRING
	 */
	
	
	public static boolean isEmptyString(String s) {
		if(s==null) return true;
		return s.trim().equals("");
	}
	
	/*
	 * EMPTY NUMBER
	 */
	
	public static boolean isEmptyPosNumber(Number n) {
		if(n==null) return true;
		return n.doubleValue()<0;
	}
	
	public static boolean isEmptyPosStrictNumber(Number n) {
		if(n==null) return true;
		return n.doubleValue()<=0;
	}
	
	public static boolean isEmptyRelativeNumber(Number n) {
		return n==null;
	}
	
	/*
	 * EMPTY COL / MAP / ARRAY / FILE
	 */
	
	public static boolean isEmptyCol(Collection<?> c) {
		if(c==null) return true;
		return c.isEmpty();
	}
	
	public static boolean isEmptyMap(Map<?,?> m) {
		if(m==null) return true;
		return m.isEmpty();
	}
	
	public static boolean isEmptyArray(Object[] a) {
		if(a==null) return true;
		return a.length==0;
	}
	
	public static boolean isEmptyFile(File f) {
		if(f==null) return true;
		if(!f.isFile()) return true;
		return f.length()==0;
	}
}