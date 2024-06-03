package gus.game5.core.util;

import java.util.List;

import gus.game5.core.features.t.T;

public class UtilRegexRule2 {
	
	/*
	 * BUILD REGEX
	 */

	public static String buildRegex(String rule) {
		String[] n = rule.split("\\*",-1);
		return buildRegex(n);
	}

	public static String buildRegex(String[] n) {
		StringBuffer b = new StringBuffer();
		for(int i=0;i<n.length;i++) {
			b.append(UtilRegex.quote(n[i]));
			if(i<n.length-1) b.append(".+?");
		}
		return b.toString();
	}
	
	/*
	 * EXTRACT
	 * 
	 * - extractAll
	 * - extractFirst
	 * - extractLast
	 * - extractAt
	 */

	/*
	 * EXTRACT ALL
	 */

	public static List<String> extractAll(String text, String rule) {
		String regex = buildRegex(rule);
		return UtilRegex.extractAll(text, regex);
	}
	
	/*
	 * EXTRACT FIRST
	 */

	public static String extractFirst(String text, String rule) {
		String regex = buildRegex(rule);
		return UtilRegex.extractFirst(text, regex);
	}
	
	/*
	 * EXTRACT LAST
	 */

	public static String extractLast(String text, String rule) {
		String regex = buildRegex(rule);
		return UtilRegex.extractLast(text, regex);
	}
	
	/*
	 * EXTRACT AT
	 */

	public static String extractAt(String text, String rule, int index) {
		String regex = buildRegex(rule);
		return UtilRegex.extractAt(text, regex, index);
	}
	
	
	
	/*
	 * REPLACE
	 * 
	 * - replaceAll
	 * - replaceFirst
	 * - replaceLast
	 * - replaceAt
	 */
	
	/*
	 * REPLACE ALL
	 */
	
	public static String replaceAll(String text, String rule, T<String, String> t) {
		String regex = buildRegex(rule);
		return UtilRegex.replaceAll(text, regex, t);
	}
	
	/*
	 * REPLACE FIRST
	 */
	
	public static String replaceFirst(String text, String rule, T<String, String> t) {
		String regex = buildRegex(rule);
		return UtilRegex.replaceFirst(text, regex, t);
	}
	
	/*
	 * REPLACE LAST
	 */
	
	public static String replaceLast(String text, String rule, T<String, String> t) {
		String regex = buildRegex(rule);
		return UtilRegex.replaceLast(text, regex, t);
	}
	
	/*
	 * REPLACE AT
	 */
	
	public static String replaceAt(String text, String rule, T<String, String> t, int index) {
		String regex = buildRegex(rule);
		return UtilRegex.replaceAt(text, regex, t, index);
	}
}