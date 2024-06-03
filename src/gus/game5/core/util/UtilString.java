package gus.game5.core.util;

import static gus.game5.core.util.UtilDiacritics.normalize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gus.game5.core.exception.TechnicalException;
import gus.game5.core.features.f.F;
import gus.game5.core.features.t.T;
import gus.game5.core.features.t.Ts;

public class UtilString {
	
	/*
	 * CHECK
	 */
	
	public static String check(Object obj) {
		if(obj==null) return null;
		if(obj instanceof String) return (String) obj;
		throw new TechnicalException("Invalid data type: "+obj.getClass().getName());
	}
	
	public static String checkNotNull(String s_) {
		if(s_==null) throw new TechnicalException("Invalid null string detected!");
		return s_;
	}
	
	public static String checkNotEmpty(String s) {
		if(isEmpty(s)) throw new TechnicalException("Invalid empty string detected!");
		return s;
	}
	
	public static String checkNotBlank(String s) {
		if(isBlank(s)) throw new TechnicalException("Invalid blank string detected!");
		return s;
	}
	
	
	/*
	 * TO STRING
	 */
	
	public static String objToString(Object obj) {
		if(obj==null) return null;
		
		if(obj instanceof String) return (String) obj;
		if(obj instanceof Number) return ""+obj;
		if(obj instanceof Boolean) return ""+obj;
		if(obj instanceof Date) return UtilDate.display((Date) obj);
		
		throw new TechnicalException("Failed to convert data into String (Unsupported type: "+obj.getClass().getName()+")");
	}
	
	
	/*
	 * AVOID NULL
	 */
	
	public static String avoidNull(String s, String defaultValue) {
		return s!=null ? s : defaultValue;
	}
	
	public static String avoidNull(String s) {
		return avoidNull(s,"");
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


	
	
	/*
	 * IS CASE
	 */
	
	public static boolean isCamelCase(String s) {
		return s!=null && s.matches("[a-z][a-zA-Z0-9]*");
	}
	
	public static boolean isPascalCase(String s) {
		return s!=null && s.matches("[A-Z][a-zA-Z0-9]*");
	}
	
	public static boolean isLowerSnakeCase(String s) {
		return s!=null && s.matches("[a-z][a-z0-9]*(_[a-z0-9]*)*");
	}
	
	public static boolean isUpperSnakeCase(String s) {
		return s!=null && s.matches("[A-Z][A-Z0-9]*(_[A-Z0-9]*)*");
	}
	
	public static boolean isLowerKebabCase(String s) {
		return s!=null && s.matches("[a-z][a-z0-9]*(-[a-z0-9]*)*");
	}
	
	public static boolean isUpperKebabCase(String s) {
		return s!=null && s.matches("[A-Z][A-Z0-9]*(-[A-Z0-9]*)*");
	}
	
	public static boolean isLowerPointCase(String s) {
		return s!=null && s.matches("[a-z][a-z0-9]*(\\.[a-z0-9]*)*");
	}
	
	public static boolean isUpperPointCase(String s) {
		return s!=null && s.matches("[A-Z][A-Z0-9]*(\\.[A-Z0-9]*)*");
	}
	
	public static boolean isLowerSpaceCase(String s) {
		return s!=null && s.matches("[a-z][a-z0-9]*( [a-z0-9]*)*");
	}
	
	public static boolean isUpperSpaceCase(String s) {
		return s!=null && s.matches("[A-Z][A-Z0-9]*( [A-Z0-9]*)*");
	}
	
	
	/*
	 * TO CASE
	 * 
	 * - splitCase
	 * - splitCamelCase
	 * - toCamelCase
	 * - toPascalCase
	 * - toLowerSnakeCase
	 * - toUpperSnakeCase
	 * - toLowerKebabCase
	 * - toUpperKebabCase
	 * - toLowerPointCase
	 * - toUpperPointCase
	 * - toLowerSpaceCase
	 * - toUpperSpaceCase
	 */
	
	/*
	 * SPLIT CASE
	 */
	
	public static String[] splitCase(String s) {
		if(s==null || s.equals("")) return new String[] {};
		if(isCamelCase(s) || isPascalCase(s)) return splitCamelCase(s);
		if(isLowerSnakeCase(s) || isUpperSnakeCase(s)) return s.split("_",-1);
		if(isLowerKebabCase(s) || isUpperKebabCase(s)) return s.split("-",-1);
		if(isLowerPointCase(s) || isUpperPointCase(s)) return s.split("\\.",-1);
		if(isLowerSpaceCase(s) || isUpperSpaceCase(s)) return s.split(" ",-1);
		return new String[] {};
	}
	
	/*
	 * SPLIT CAMEL CASE
	 */
	
	public static String[] splitCamelCase(String s) {
		List<String> l = new ArrayList<>();
		int len = s.length();
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<len;i++) {
			char c = s.charAt(i);
			if(Character.isUpperCase(c) && sb.length()>0) {
				l.add(sb.toString());
				sb = new StringBuilder();
			}
			sb.append(c);
		}
		if(sb.length()>0) l.add(sb.toString());
		String[] array = new String[l.size()];
		return l.toArray(array);
	}
	
	/*
	 * MIMIC CASE
	 */
	
	public static String mimicCase(String s, String mimicked) {
		if(isLowerAlphaNum(mimicked)) return lower(s);
		if(isUpperAlphaNum(mimicked)) return upper(s);
		if(isCamelCase(mimicked)) return toCamelCase(s);
		if(isPascalCase(mimicked)) return toPascalCase(s);
		if(isLowerSnakeCase(mimicked)) return toLowerSnakeCase(s);
		if(isUpperSnakeCase(mimicked)) return toUpperSnakeCase(s);
		if(isLowerKebabCase(mimicked)) return toLowerKebabCase(s);
		if(isUpperKebabCase(mimicked)) return toUpperKebabCase(s);
		if(isLowerPointCase(mimicked)) return toLowerPointCase(s);
		if(isUpperPointCase(mimicked)) return toUpperPointCase(s);
		if(isLowerSpaceCase(mimicked)) return toLowerSpaceCase(s);
		if(isUpperSpaceCase(mimicked)) return toUpperSpaceCase(s);
		return s;
	}
	
	/*
	 * TO CAMEL CASE
	 */
	
	public static String toCamelCase(String s) {
		return toCamelCase(splitCase(s));
	}
	
	public static String toCamelCase(String[] nn) {
		StringBuilder sb = new StringBuilder();
		int len = nn.length;
		for(int i=0;i<len;i++) sb.append(i==0 ? lower(nn[i]) : titled(nn[i]));
		return sb.toString();
	}
	
	/*
	 * TO PASCAL CASE
	 */
	
	public static String toPascalCase(String s) {
		return toPascalCase(splitCase(s));
	}
	
	public static String toPascalCase(String[] nn) {
		StringBuilder sb = new StringBuilder();
		int len = nn.length;
		for(int i=0;i<len;i++) sb.append(titled(nn[i]));
		return sb.toString();
	}
	
	/*
	 * TO LOWER SNAKE CASE
	 */
	
	public static String toLowerSnakeCase(String s) {
		return toLowerSnakeCase(splitCase(s));
	}
	
	public static String toLowerSnakeCase(String[] nn) {
		StringBuilder sb = new StringBuilder();
		int len = nn.length;
		for(int i=0;i<len;i++) {
			sb.append(nn[i].toLowerCase());
			if(i<len-1) sb.append("_");
		}
		return sb.toString();
	}
	
	/*
	 * TO UPPER SNAKE CASE
	 */
	
	public static String toUpperSnakeCase(String s) {
		return toUpperSnakeCase(splitCase(s));
	}
	
	public static String toUpperSnakeCase(String[] nn) {
		StringBuilder sb = new StringBuilder();
		int len = nn.length;
		for(int i=0;i<len;i++) {
			sb.append(nn[i].toUpperCase());
			if(i<len-1) sb.append("_");
		}
		return sb.toString();
	}
	
	/*
	 * TO LOWER KEBAB CASE
	 */
	
	public static String toLowerKebabCase(String s) {
		return toLowerKebabCase(splitCase(s));
	}
	
	public static String toLowerKebabCase(String[] nn) {
		StringBuilder sb = new StringBuilder();
		int len = nn.length;
		for(int i=0;i<len;i++) {
			sb.append(nn[i].toLowerCase());
			if(i<len-1) sb.append("-");
		}
		return sb.toString();
	}
	
	/*
	 * TO UPPER KEBAB CASE
	 */
	
	public static String toUpperKebabCase(String s) {
		return toUpperKebabCase(splitCase(s));
	}
	
	public static String toUpperKebabCase(String[] nn) {
		StringBuilder sb = new StringBuilder();
		int len = nn.length;
		for(int i=0;i<len;i++) {
			sb.append(nn[i].toUpperCase());
			if(i<len-1) sb.append("-");
		}
		return sb.toString();
	}
	
	/*
	 * TO LOWER POINT CASE
	 */
	
	public static String toLowerPointCase(String s) {
		return toLowerPointCase(splitCase(s));
	}
	
	public static String toLowerPointCase(String[] nn) {
		StringBuilder sb = new StringBuilder();
		int len = nn.length;
		for(int i=0;i<len;i++) {
			sb.append(nn[i].toLowerCase());
			if(i<len-1) sb.append(".");
		}
		return sb.toString();
	}
	
	/*
	 * TO UPPER POINT CASE
	 */
	
	public static String toUpperPointCase(String s) {
		return toUpperPointCase(splitCase(s));
	}
	
	public static String toUpperPointCase(String[] nn) {
		StringBuilder sb = new StringBuilder();
		int len = nn.length;
		for(int i=0;i<len;i++) {
			sb.append(nn[i].toUpperCase());
			if(i<len-1) sb.append(".");
		}
		return sb.toString();
	}
	
	/*
	 * TO LOWER SPACE CASE
	 */
	
	public static String toLowerSpaceCase(String s) {
		return toLowerSpaceCase(splitCase(s));
	}
	
	public static String toLowerSpaceCase(String[] nn) {
		StringBuilder sb = new StringBuilder();
		int len = nn.length;
		for(int i=0;i<len;i++) {
			sb.append(nn[i].toLowerCase());
			if(i<len-1) sb.append(" ");
		}
		return sb.toString();
	}
	
	/*
	 * TO UPPER SPACE CASE
	 */
	
	public static String toUpperSpaceCase(String s) {
		return toUpperSpaceCase(splitCase(s));
	}
	
	public static String toUpperSpaceCase(String[] nn) {
		StringBuilder sb = new StringBuilder();
		int len = nn.length;
		for(int i=0;i<len;i++) {
			sb.append(nn[i].toUpperCase());
			if(i<len-1) sb.append(" ");
		}
		return sb.toString();
	}	
	

	
	
	
	/*
	 * IS ANY
	 */
	
	public static boolean isAnyEmpty(String... ss) {
		return UtilArray.any(ss,s->isEmpty(s));
	}
	
	public static boolean isAnyBlank(String... ss) {
		return UtilArray.any(ss,s->isBlank(s));
	}
	
	public static boolean isAnyFilled(String... ss) {
		return UtilArray.any(ss,s->isFilled(s));
	}
	
	public static boolean isAnyUpper(String... ss) {
		return UtilArray.any(ss,s->isUpper(s));
	}
	
	public static boolean isAnyLower(String... ss) {
		return UtilArray.any(ss,s->isLower(s));
	}
	
	public static boolean isAnyIdentifier(String... ss) {
		return UtilArray.any(ss,s->isIdentifier(s));
	}
	
	
	/*
	 * IS ALL
	 */
	
	public static boolean isAllEmpty(String... ss) {
		return UtilArray.all(ss,s->isEmpty(s));
	}
	
	public static boolean isAllBlank(String... ss) {
		return UtilArray.all(ss,s->isBlank(s));
	}
	
	public static boolean isAllFilled(String... ss) {
		return UtilArray.all(ss,s->isFilled(s));
	}
	
	public static boolean isAllUpper(String... ss) {
		return UtilArray.all(ss,s->isUpper(s));
	}
	
	public static boolean isAllLower(String... ss) {
		return UtilArray.all(ss,s->isLower(s));
	}
	
	public static boolean isAllIdentifier(String... ss) {
		return UtilArray.all(ss,s->isIdentifier(s));
	}
	
	
	/*
	 * EQUALS
	 */
	
	public static boolean equals(String s1, String s2) {
		if(s1==null || s2==null) return false;
		return s1.equals(s2);
	}
	
	/*
	 * STARTS WITH
	 */
	
	public static boolean startsWith(String s1, String s2) {
		if(s1==null || s2==null) return false;
		return s1.startsWith(s2);
	}
	
	public static boolean startsWith_i(String s1, String s2) {
		if(s1==null || s2==null) return false;
		return s1.toLowerCase().startsWith(s2.toLowerCase());
	}
	
	public static boolean startsWith_n(String s1, String s2) {
		if(s1==null || s2==null) return false;
		return normalize(s1).startsWith(normalize(s2));
	}
	
	/*
	 * ENDS WITH
	 */
	
	public static boolean endsWith(String s1, String s2) {
		if(s1==null || s2==null) return false;
		return s1.endsWith(s2);
	}
	
	public static boolean endsWith_i(String s1, String s2) {
		if(s1==null || s2==null) return false;
		return s1.toLowerCase().endsWith(s2.toLowerCase());
	}
	
	public static boolean endsWith_n(String s1, String s2) {
		if(s1==null || s2==null) return false;
		return normalize(s1).endsWith(normalize(s2));
	}
	
	/*
	 * CONTAINS
	 */
	
	public static boolean contains(String s1, String s2) {
		if(s1==null || s2==null) return false;
		return s1.contains(s2);
	}
	
	public static boolean contains_i(String s1, String s2) {
		if(s1==null || s2==null) return false;
		return s1.toLowerCase().contains(s2.toLowerCase());
	}
	
	public static boolean contains_n(String s1, String s2) {
		if(s1==null || s2==null) return false;
		return normalize(s1).contains(normalize(s2));
	}
	
	/*
	 * MATCHES
	 */
	
	public static boolean matches(String s, String regex) {
		if(s==null || regex==null) return false;
		return s.matches(regex);
	}
	
	public static boolean matches_i(String s, String regex) {
		if(s==null || regex==null) return false;
		return s.toLowerCase().matches(regex);
	}
	
	public static boolean matches_n(String s, String regex) {
		if(s==null || regex==null) return false;
		return normalize(s).matches(regex);
	}
	
	/*
	 * IS INT DOUBLE LONG
	 */
	
	public static boolean isInt(String s) {
		if(s==null) return false;
		try {Integer.parseInt(s);return true;}
		catch(NumberFormatException e) {return false;}
	}
	
	public static boolean isDouble(String s) {
		if(s==null) return false;
		try {Double.parseDouble(s);return true;}
		catch(NumberFormatException e) {return false;}
	}
	
	public static boolean isLong(String s) {
		if(s==null) return false;
		try {Long.parseLong(s);return true;}
		catch(NumberFormatException e) {return false;}
	}
	
	/*
	 * LOWER
	 */
	
	public static String lower(String s) {
		if(s==null || s.length()==0) return s;
		return s.toLowerCase();
	}
	
	public static String lowerC0(String s) {
		if(s==null || s.length()==0) return s;
		return s.substring(0,1).toLowerCase() + s.substring(1);
	}
	
	public static String lowerC1(String s) {
		if(s==null || s.length()==0) return s;
		int len = s.length();
		return s.substring(0,len-1) + s.substring(len-1).toLowerCase();
	}
	
	
	/*
	 * UPPER
	 */
	
	public static String upper(String s) {
		if(s==null || s.length()==0) return s;
		return s.toUpperCase();
	}
	
	public static String upperC0(String s) {
		if(s==null || s.length()==0) return s;
		return s.substring(0,1).toUpperCase() + s.substring(1);
	}
	
	public static String upperC1(String s) {
		if(s==null || s.length()==0) return s;
		int len = s.length();
		return s.substring(0,len-1) + s.substring(len-1).toUpperCase();
	}
	
	/*
	 * TITLED
	 */
	
	public static String titled(String s) {
		if(s==null || s.length()==0) return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}
	
	
	/*
	 * REMOVE ALL
	 */
	
	public static String removeAll(String s, List<String> list) {
		return UtilRegex.replaceAllFromCol(s, list, "");
	}
	
	public static String removeAll(String s, String elem) {
		return s.replace(elem, "");
	}
	
	/*
	 * REMOVE C0
	 */
	
	public static String removeC0(String s) {
		if(s==null || s.length()==0) return s;
		return s.substring(1);
	}
	
	public static String removeC0(String s, String elem) {
		if(s==null || s.length()==0) return s;
		if(!s.startsWith(elem)) return s;
		return s.substring(elem.length());
	}
	
	/*
	 * REMOVE C1
	 */
	
	public static String removeC1(String s) {
		if(s==null || s.length()==0) return s;
		return s.substring(0, s.length()-1);
	}
	
	public static String removeC1(String s, String elem) {
		if(s==null || s.length()==0) return s;
		if(!s.endsWith(elem)) return s;
		return s.substring(0, s.length()-elem.length());
	}
	
	/*
	 * REMOVE C01
	 */
	
	public static String removeC01(String s) {
		if(s==null) return null;
		if(s.length()<2) return "";
		return s.substring(1, s.length()-1);
	}
	
	public static String removeC01(String s, String elem1) {
		return removeC1(removeC0(s,elem1),elem1);
	}
	
	public static String removeC01(String s, String elem1, String elem2) {
		return removeC1(removeC0(s,elem1),elem2);
	}
	
	/*
	 * REMOVE C
	 */
	
	public static String removeC(String s, int[] indexes) {
		if(s==null) return null;
		StringBuilder b = new StringBuilder();
		int len = s.length();
		for(int i=0;i<indexes.length;i++) {
			if(indexes[i]<0) indexes[i] += len;
		}
		for(int i=0;i<len;i++) {
			if(!UtilInteger.contains(indexes, i)) b.append(s.charAt(i));
		}
		return b.toString();
	}
	
	public static String removeC(String s, String removed) {
		if(s==null) return null;
		StringBuilder b = new StringBuilder();
		int len = s.length();
		for(int i=0;i<len;i++) {
			char c = s.charAt(i);
			if(removed.indexOf((int) c)<0) b.append(c);
		}
		return b.toString();
	}
	
	/*
	 * INSERT
	 */
	
	public static String insert(String s, String inserted, int pos) {
		if(s==null || inserted==null) return s;
		
		int len = s.length();
		if(pos<0) pos += len+1;
		
		if(pos<0 || pos>len) return s;
		if(pos==len) return s+inserted;
		return s.substring(0, pos) + inserted + s.substring(pos);
	}
	
	/*
	 * INSERT BEFORE
	 */
	
	public static String insertB(String s, String inserted, String delim, int index) {
		String[] nn = split(s, delim);
		int len = nn.length;

		if(index<0) index += len;
		if(index<0 || index>len-1) return s;
		
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<index; i++) {
			sb.append(nn[i]);
			if(i<index-1) sb.append(delim);
		}
		return sb.toString();
	}
	
	/*
	 * REVERSE
	 */
	
	public static String reverse(String s) {
		if(s==null) return null;
		return new StringBuffer(s).reverse().toString();
	}
	
	/*
	 * SHUFFLE
	 */
	
	public static String shuffle(String s) {
		if(s==null) return null;

		StringBuffer b0 = new StringBuffer(s);
		StringBuffer b1 = new StringBuffer();
		
		while(b0.length()>0)
		{
			int n = (int) (Math.random()*b0.length());
			b1.append(b0.charAt(n));
			b0.deleteCharAt(n);
		}
		return b1.toString();
	}
	
	/*
	 * MULT
	 */
	
	public static String mult(String s, int n) {
		if(s==null) return null;
		
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<n;i++) sb.append(s);
		return sb.toString();
	}
	
	/*
	 * REPLACE ALL
	 */
	
	public static String replaceAll(String s, String s1, String s2) {
		return s.replace(s1, s2);
	}
	
	/*
	 * REPLACE FIRST
	 */
	
	public static String replaceFirst(String s, String s1, String s2) {
		String s1_ = UtilRegex.quote(s1);
		String s2_ = Matcher.quoteReplacement(s2);
		return s.replaceFirst(s1_, s2_);
	}
	
	/*
	 * REPLACE LAST
	 */
	
	public static String replaceLast(String s, String s1, String s2) {
		String s1_ = UtilRegex.quote(s1);
		String s2_ = Matcher.quoteReplacement(s2);
		return s.replaceFirst("(?session_)"+s1_+"(?!.*?"+s1_+")", s2_);
	}
	
	/*
	 * REPLACE AT
	 */
	
	public static String replaceAt(String s, String s1, String s2, int index) {
		String s1_ = UtilRegex.quote(s1);
		String s2_ = Matcher.quoteReplacement(s2);
		return UtilRegex.replaceAt(s, s1_, s2_, index);
	}
	
	/*
	 * REPLACE START
	 */
	
	public static String replaceStart(String s, String s1, String s2) {
		if(s.startsWith(s1)) return s2 + s.substring(s1.length());
		return s;
	}
	
	/*
	 * REPLACE END
	 */
	
	public static String replaceEnd(String s, String s1, String s2) {
		if(s.endsWith(s1)) return s.substring(s.length() - s1.length(), s.length()) + s2;
		return s;
	}
	
	/*
	 * SMART REPLACE ALL
	 */
	
	public static String smartReplaceAll(String s, String s1, String s2) {
		return UtilRegex.replaceAllFromMap(s, buildCaseMap(s1,s2));
	}
	
	public static String smartReplaceAll(String s, String s1, T<String,String> t) {
		return UtilRegex.replaceAllFromCol(s, buildCaseSet(s1), t);
	}
	
	/*
	 * SMART EXTRACT ALL
	 */
	
	public static List<String> smartExtractAll(String s, String s1) {
		return UtilRegex.extractAllFromCol(s, buildCaseSet(s1));
	}
	
	/*
	 * BUILD CASE MAP
	 */
	
	public static Map<String,String> buildCaseMap(String s1, String s2) {
		Map<String,String> map = new HashMap<>();
		map.put(toCamelCase(s1), toCamelCase(s2));
		map.put(toPascalCase(s1), toPascalCase(s2));
		map.put(toLowerSnakeCase(s1), toLowerSnakeCase(s2));
		map.put(toLowerKebabCase(s1), toLowerKebabCase(s2));
		map.put(toLowerPointCase(s1), toLowerPointCase(s2));
		map.put(toLowerSpaceCase(s1), toLowerSpaceCase(s2));
		map.put(toUpperSnakeCase(s1), toUpperSnakeCase(s2));
		map.put(toUpperKebabCase(s1), toUpperKebabCase(s2));
		map.put(toUpperPointCase(s1), toUpperPointCase(s2));
		map.put(toUpperSpaceCase(s1), toUpperSpaceCase(s2));
		map.put(lower(s1), lower(s2));
		map.put(upper(s1), upper(s2));
		return map;
	}
	
	/*
	 * BUILD CASE SET
	 */
	
	public static Set<String> buildCaseSet(String s1) {
		Set<String> set = new HashSet<>();
		set.add(toCamelCase(s1));
		set.add(toPascalCase(s1));
		set.add(toLowerSnakeCase(s1));
		set.add(toLowerKebabCase(s1));
		set.add(toLowerPointCase(s1));
		set.add(toLowerSpaceCase(s1));
		set.add(toUpperSnakeCase(s1));
		set.add(toUpperKebabCase(s1));
		set.add(toUpperPointCase(s1));
		set.add(toUpperSpaceCase(s1));
		set.add(lower(s1));
		set.add(upper(s1));
		return set;
	}
	
	
	/*
	 * DISPLAY
	 */
	
	public static String display(String s) {
		if(s==null) return "";
		return s.trim();
	}
	
	/*
	 * SPLIT
	 */
	
	public static String[] split(String s, String delim) {
		return split(s,delim,-1);
	}
	
	public static String[] split(String s, String delim, int limit) {
		return isEmpty(s) ? new String[0] : s.split(Pattern.quote(delim),limit);
	}
	
	public static List<String> splitAsList(String s, String delim) {
		return Arrays.asList(split(s,delim));
	}
	
	public static List<String> splitAsList(String s, String delim, int limit) {
		return Arrays.asList(split(s,delim, limit));
	}
	
	/*
	 * SPLIT TRIMED
	 */
	
	public static List<String> splitTrimed(String s, String delim) {
		return UtilList.collect(split(s,delim),String::trim);
	}
	
	/*
	 * SPLIT SEQUENCE
	 */
	
	public static String[] splitSequence(String seq) {
		return split(seq,";");
	}
	
	public static List<String> splitSequenceAsList(String seq) {
		return splitAsList(seq,";");
	}
	
	/*
	 * SUB
	 */
	
	public static String sub(String s, int start) {
		int len = s.length();
		if(start<0) start += len;

		if(start<0) return s;
		if(start>len-1) return "";
		return s.substring(start);
	}
	
	public static String sub(String s, int start, int end) {
		int len = s.length();
		
		if(start<0) start += len;
		if(end<=0) end += len;
		
		if(start>len-1) return "";
		if(end<0) return "";
		if(start>=end) return "";
		
		return s.substring(start,end);
	}
	
	/*
	 * SUB BEFORE
	 */
	
	public static String subB(String s, String delim, int index) {
		String[] nn = split(s, delim);
		int len = nn.length;

		if(index<0) index += len;
		if(index<0 || index>len-1) return s;
		
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<index; i++) {
			sb.append(nn[i]);
			if(i<index-1) sb.append(delim);
		}
		return sb.toString();
	}
	
	/*
	 * SUB AFTER
	 */
	
	public static String subA(String s, String delim, int index) {
		String[] nn = split(s, delim);
		int len = nn.length;

		if(index<0) index += len;
		if(index<0 || index>len-1) return s;
		
		StringBuilder sb = new StringBuilder();
		for(int i=index;i<len; i++) {
			sb.append(nn[i]);
			if(i<len-1) sb.append(delim);
		}
		return sb.toString();
	}
	
	/*
	 * TRUNCATE
	 */
	
	public static String truncate(String s, int len) {
		if(s.length()<=len) return s;
		return s.substring(0,len);
	}
	
	/*
	 * TRUNCATE PRETTY
	 */
	
	public static String truncatePretty(String s, int len) {
		if(s.length()<=len) return s;
		return s.substring(0,len)+"...";
	}
	
	/*
	 * GEN STAR
	 */
	
	public static String genStar(String s, String rule) {
		return rule.replace("*", s);
	}
	
	/*
	 * JOIN
	 */
	
	public static String join(String[] seq, String glue) {
		if(seq==null || seq.length==0) return "";
		StringJoiner sj = new StringJoiner(glue);
		for(String s : seq) if(isFilled(s)) sj.add(s);
		return sj.toString();
	}
	
	public static String join(List<String> list, String glue) {
		if(list==null || list.isEmpty()) return "";
		StringJoiner sj = new StringJoiner(glue);
		for(String s : list) if(isFilled(s)) sj.add(s);
		return sj.toString();
	}
	
	public static String join(Set<String> set, String glue) {
		return join(UtilList.sort(set),glue);
	}
	
	public static <U> String join(U[] seq, T<U,String> t, String glue) {
		if(seq==null || seq.length==0) return "";
		StringJoiner sj = new StringJoiner(glue);
		for(U u : seq) {
			String s = t.t(u);
			if(isFilled(s)) sj.add(s);
		}
		return sj.toString();
	}
	
	public static <U> String join(List<U> list, T<U,String> t, String glue) {
		if(list==null || list.isEmpty()) return "";
		StringJoiner sj = new StringJoiner(glue);
		for(U u : list) {
			String s = t.t(u);
			if(isFilled(s)) sj.add(s);
		}
		return sj.toString();
	}
	
	
	/*
	 * JOIN WITH EMPTY GLUE
	 */
	
	public static String join(String... seq) {
		return join(seq,"");
	}
	public static String join(List<String> list) {
		return join(list,"");
	}
	public static String join(Set<String> set) {
		return join(set,"");
	}
	public static <U> String join(U[] seq, T<U,String> t) {
		return join(seq,t,"");
	}
	public static <U> String join(List<U> list, T<U,String> t) {
		return join(list,t,"");
	}
	
	
	/*
	 * JOIN WITH SPACE GLUE
	 */
	
	public static String join1(String... seq) {
		return join(seq," ");
	}
	public static String join1(List<String> list) {
		return join(list," ");
	}
	public static String join1(Set<String> set) {
		return join(set," ");
	}
	public static <U> String join1(U[] seq, T<U,String> t) {
		return join(seq,t," ");
	}
	public static <U> String join1(List<U> list, T<U,String> t) {
		return join(list,t," ");
	}
	
	
	/*
	 * COUNT
	 */
	
	public static int count(String s, char c) {
		int count = 0;
		for(int i=0;i<s.length();i++) {
			if(s.charAt(i)==c) count++;
		}
		return count;
	}
	
	public static int count(String s, String elem) {
		return UtilRegex.count(s, UtilRegex.quote(elem));
	}
	
	
	/*
	 * SMART COUNT
	 */
	
	public static int smartCount(String s, String elem) {
		return UtilRegex.countFromCol(s, buildCaseSet(elem));
	}
	
	
	/*
	 * OBFUSCATE
	 */
	
	public static String obfuscate(String s, char c) {
		StringBuilder b = new StringBuilder();
		for(int i=0;i<s.length();i++) b.append(c);
		return b.toString();
	}
	
	public static String obfuscate(String s, char c, int offset) {
		StringBuilder b = new StringBuilder();
		
		int len = s.length();
		if(offset<0) offset += len;
		
		for(int i=0;i<len;i++) b.append(i>offset ? c : s.charAt(i));
		return b.toString();
	}
	
	public static String obfuscate(String s, char c, int offset, int tail) {
		StringBuilder b = new StringBuilder();
		
		int len = s.length();
		if(offset<0) offset += len;
		if(tail<0) tail += len;
		
		for(int i=0;i<len;i++) b.append(i>offset && i<tail ? c : s.charAt(i));
		return b.toString();
	}
	
	
	
	
	

	
	/*
	 * CONTAINS
	 */
	
	public static boolean sequenceContains(String seq, String elem) {
		if(seq==null) return false;
		return sequenceContains(splitSequence(seq),elem);
	}
	
	public static boolean sequenceContains(String[] seq, String elem) {
		if(elem==null || seq==null) return false;
		for(String n : seq) {
			if(Objects.equals(elem,n)) return true;
		}
		return false;
	}
	
	
	/*
	 * IS ONE OF
	 */
	
	public static boolean isOneOf(String s, String... vv) {
		for(String v : vv) if(equals(v,s)) return true;
		return false;
	}
	
	
	/*
	 * TITLE
	 */
	
	public static String title(String s) {
		if(isBlank(s)) return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
	
	public static String firstPart(String s, String delim) {
		String[] n = s.split(Pattern.quote(delim));
		return n[0];
	}
	
	
	public static String lastPart(String s, String delim) {
		String[] n = s.split(Pattern.quote(delim));
		return n[n.length-1];
	}
	
	
	public static boolean containsWord(String s1, String s2) {
		if(s1==null || s2==null) return false;
		String s1_ = " "+s1+" ";
		String s2_ = " "+s2+" ";
		return s1_.contains(s2_);
	}
	
	/*
	 * FIRST LETTER UPPER
	 */
	
	public static String firstLetterUpper(String s) {
		if(s==null || s.length()==0) return "";
		return String.valueOf(Character.toUpperCase(s.charAt(0)));
	}
	
	
	/*
	 * TS
	 */

	public static Ts<String> UPPER = s->upper(s);
	public static Ts<String> UPPER_C0 = s->upperC0(s);
	public static Ts<String> LOWER = s->lower(s);
	
	
	public static Ts<String> templateT(String rule, String repl) {
		return s->rule.replace(repl, s);
	}
	
	public static Ts<String> templateT(String rule) {
		return templateT(rule,"*");
	}
	
	public static Ts<String> removeT(String removed) {
		return s->s.replace(removed,"");
	}
	
	/*
	 * PRINT
	 */
	
	
	public static String buildPrint(List<String> names) {
		Collections.sort(names);
		return join(names,";");
	}
	

	
	/*
	 * SUM
	 */
	
	public static String sum(String... values) {
		StringBuilder b = new StringBuilder();
		if(values!=null) for (String value : values) 
			if(value!=null) b.append(value);
		return b.toString();
	}
	
	public static String sumString(List<String> list) {
		StringBuilder b = new StringBuilder();
		if(list!=null) for (String value : list)
			if(value!=null) b.append(value);
		return b.toString();
	}
	
	public static <U> String sum(List<U> list, T<U, String> t) {
		StringBuilder b = new StringBuilder();
		if(list!=null) for (U elem : list) {
			String value = t.t(elem);
			if(value!=null) b.append(value);
		}
		return b.toString();
	}
	
	/*
	 * SUM WHERE
	 */

	public static <U> String sumWhere(List<U> list, T<U, String> t, F<U> f) {
		StringBuilder b = new StringBuilder();
		if(list!=null) for (U elem : list) {
			if(f == null || f.f(elem)) {
				String value = t.t(elem);
				if(value!=null) b.append(value);
			}
		}
		return b.toString();
	}
}
