package gus.game5.core.util;

import java.util.List;
import java.util.StringJoiner;

public class UtilString {

	
	public static String join(List<String> list, String glue) {
		if(list==null || list.isEmpty()) return "";
		StringJoiner sj = new StringJoiner(glue);
		for(String s : list) if(isFilled(s)) sj.add(s);
		return sj.toString();
	}
	

	/*
	 * IS
	 */
	
	public static boolean isEmpty(String s) {
		return s==null || s.length()==0;
	}
	
	public static boolean isBlank(String s) {
		return s==null || s.trim().length()==0;
	}
	
	public static boolean isFilled(String s) {
		return !isBlank(s);
	}
	
	public static boolean isLower(String s) {
		return s!=null && s.toLowerCase().equals(s);
	}
	
	public static boolean isUpper(String s) {
		return s!=null && s.toUpperCase().equals(s);
	}
	
	public static boolean isAlpha(String s) {
		return s!=null && s.matches("[a-zA-Z]+");
	}
	
	public static boolean isAlphaNum(String s) {
		return s!=null && s.matches("[a-zA-Z0-9]+");
	}
	
	public static boolean isLowerAlpha(String s) {
		return s!=null && s.matches("[a-z]+");
	}
	
	public static boolean isLowerAlphaNum(String s) {
		return s!=null && s.matches("[a-z0-9]+");
	}
	
	public static boolean isUpperAlpha(String s) {
		return s!=null && s.matches("[A-Z]+");
	}
	
	public static boolean isUpperAlphaNum(String s) {
		return s!=null && s.matches("[A-Z0-9]+");
	}
	
	public static boolean isHexa(String s) {
		return s!=null && s.matches("[a-fA-F0-9]+");
	}
	
	public static boolean isLowerHexa(String s) {
		return s!=null && s.matches("[a-f0-9]+");
	}
	
	public static boolean isUpperHexa(String s) {
		return s!=null && s.matches("[A-F0-9]+");
	}
	
	public static boolean isIdentifier(String s) {
		return s!=null && s.matches("[a-zA-Z][a-zA-Z0-9_]*");
	}
}
